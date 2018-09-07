/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.history;

import com.tazz.web3jWrapper.contracts.ContractService;
import com.tazz.web3jWrapper.contracts.ERC20Wrapper;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import rx.Completable;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.BlockingObservable;
import rx.subjects.PublishSubject;

/**
 *
 * @author Tazzmanian
 */
@Slf4j
@Service
public class HistoryService {

    private final ContractService contractService;

    public HistoryService(ContractService contractService) {
        this.contractService = contractService;
    }

    public List<HistoryResponseDTO> getHistory(HistoryRequestDTO dto) {
        List<HistoryResponseDTO> res = new ArrayList<>();
        Observable ob = null;
        Subscription sub = null;
        try {
            Web3j web3 = Web3j.build(new HttpService(dto.getNetwork()));
            DefaultBlockParameter start = DefaultBlockParameter.valueOf(dto.getBlockNumber());

            log.info("Number of active threads from the given thread 1: " + Thread.activeCount());

            ob = web3.catchUpToLatestTransactionObservable(start).asObservable();

            ob.toBlocking().forEach(new Action1<Transaction>() {
                @Override
                public void call(Transaction tx) {
                    log.info("Number of active threads from the given thread 2: " + Thread.activeCount());
                    if (tx.getNonce().compareTo(dto.getNonce()) > 0) {
                        HistoryResponseDTO r = new HistoryResponseDTO();
                        r.setBlockNumber(tx.getBlockNumber().toString());
                        r.setFrom(tx.getFrom());
                        r.setTo(tx.getTo());
                        r.setNonce(tx.getNonce().toString());
                        r.setNetwork(dto.getNetwork());
                        r.setAmount(Convert.fromWei(tx.getValue().toString(), Convert.Unit.ETHER).toPlainString());
                        r.setContract(tx.getTo());
                        r.setTxHash(tx.getHash());
                        r.setContract("");
                        r.setCurrency("ETH");
                        log.info("===============================");
                        log.info("from " + tx.getFrom());
                        log.info("hash " + tx.getHash());
                        log.info("input " + tx.getInput());
                        log.info("to " + tx.getTo());
                        log.info("block number " + tx.getBlockNumber().toString());
                        log.info("nonce " + tx.getNonce().toString());
                        log.info("value " + tx.getValue().toString());
                        if (r.getTo().equals("0x0")) {
                            createdContract(r, web3);
                        } else if ((!dto.getAddress().equals(r.getFrom()) || dto.getAddress().equals(r.getTo()))
                                && (dto.getAddress().equals(r.getFrom()) || !dto.getAddress().equals(r.getTo()))) {
                            transfert(tx.getInput(), r, web3);
                        }

                        if (dto.getAddress().equals(r.getFrom()) || dto.getAddress().equals(r.getTo())) {
                            res.add(r);
                        }
                    }
                    log.info("Number of active threads from the given thread 3: " + Thread.activeCount());
                }
            });

        } catch (Exception ex) {
            log.info("History: " + ex.getMessage());
        }

        ob.subscribe().unsubscribe();
        log.info("Number of active threads from the given thread 4: " + Thread.activeCount());
        return res;
    }

    private void transfert(String input, HistoryResponseDTO res, Web3j web3) {
        Method refMethod = null;
        try {
            if (input.isEmpty()) {
                return;
            }
//            String inputData = "0xa9059cbb0000000000000000000000005c5212ed85cc957c6b656d209a7be8812ab00e330000000000000000000000000000000000000000000000008d8dadf544fc0000";
            String method = input.substring(0, 10);
            System.out.println(method);
            log.info("method: " + method);
            String to = input.substring(10, 74);
            String value = input.substring(74);
            refMethod = TypeDecoder.class.getDeclaredMethod("decode", String.class, int.class, Class.class);
            refMethod.setAccessible(true);
            Address address = (Address) refMethod.invoke(null, to, 0, Address.class);
            System.out.println(address.toString());
            res.setContract(res.getTo());
            res.setTo(address.toString());
            log.info("address: " + address.toString());
            Uint256 amount = (Uint256) refMethod.invoke(null, value, 0, Uint256.class);
            System.out.println(amount.getValue());
            res.setAmount(amount.getValue().toString());
            log.info("amount: " + amount.getValue());

            ClientTransactionManager clientManager = new ClientTransactionManager(web3, res.getTo());
            ERC20Wrapper contract = ERC20Wrapper.load(res.getContract(), web3, clientManager, DefaultGasProvider.GAS_PRICE, Transfer.GAS_LIMIT);

            res.setCurrency(contract.symbol().send());
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            refMethod.close();
        }
    }

    private void createdContract(HistoryResponseDTO dto, Web3j web3) {
        Method refMethod = null;
        try {
            EthGetTransactionReceipt rec = web3.ethGetTransactionReceipt(dto.getTxHash()).send();
            List<Log> logs = rec.getResult().getLogs();
            dto.setContract(logs.get(0).getAddress());
            refMethod = TypeDecoder.class.getDeclaredMethod("decode", String.class, int.class, Class.class);
            refMethod.setAccessible(true);
            Uint256 amount = (Uint256) refMethod.invoke(null, logs.get(0).getData(), 0, Uint256.class);
            System.out.println(amount.getValue());
            dto.setAmount(amount.getValue().toString());

            ClientTransactionManager clientManager = new ClientTransactionManager(web3, dto.getFrom());
            ERC20Wrapper contract = ERC20Wrapper.load(dto.getContract(), web3, clientManager, DefaultGasProvider.GAS_PRICE, Transfer.GAS_LIMIT);

            dto.setCurrency(contract.symbol().send());

            logs.forEach(l -> {
                log.info("++++++");
                log.info(l.getAddress());
                log.info(l.getBlockHash());
                log.info(l.getBlockNumberRaw());
                log.info(l.getData());
                log.info(l.getLogIndexRaw());
                log.info(l.getTransactionHash());
                log.info(l.getTransactionIndexRaw());
                log.info(l.getType());
                log.info(l.getBlockNumber().toString());
                log.info(l.getLogIndex().toString());
                log.info(l.getTransactionIndex().toString());
                log.info("----");
                l.getTopics().forEach(t -> {
                    log.info(t.toString());
                });
                log.info("++++++");
            });
        } catch (IOException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
