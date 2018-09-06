/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.send;

import com.tazz.web3jWrapper.accounts.CreateResponseDTO;
import com.tazz.web3jWrapper.contracts.ERC20Wrapper;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

/**
 *
 * @author Tazzmanian
 */
@Slf4j
@Service
public class SendService {

    public TransactionReceipt send(SendDTO dto) {
        if (dto.getContractAddress().isEmpty()) {
            return sendEther(dto);
        } else {
            return sendToken(dto);
        }
    }

    private TransactionReceipt sendEther(SendDTO dto) {
        String pass = dto.getPassword();
        String mne = dto.getMnemonics();
        String net = dto.getNetwork();
        String to = dto.getTo();
        BigDecimal eth = dto.getEthers();
        log.info(pass);
        log.info(mne);
        log.info(net);
        log.info(to);
        log.info(eth.toString());
        Credentials cred = WalletUtils.loadBip39Credentials(pass, mne);
        Web3j web3 = Web3j.build(new HttpService(net));

        try{
            return Transfer.sendFunds(
            web3, cred, to,
            eth, Convert.Unit.ETHER)
            .send();            
        } catch(Exception e) {
            log.info("Send funds " + e.getMessage());
        }
        
        return null;
    }

    private TransactionReceipt sendToken(SendDTO dto) {
        String hex = "a9059cbb";
        log.info(new String(Numeric.hexStringToByteArray(hex)));
        try {
            Credentials cred = WalletUtils.loadBip39Credentials(dto.getPassword(), dto.getMnemonics());
            Web3j web3 = Web3j.build(new HttpService(dto.getNetwork()));

            ERC20Wrapper contract = ERC20Wrapper.load(dto.getContractAddress(), web3, cred, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
                      
            return contract.transfer(dto.getTo(), dto.getTokens()).send();
        } catch (Exception ex) {
            log.info("Send tokens ", ex.getMessage());
        }
        
        return null;
    }

    public BalanceResponceDTO balance(BalanceDTO dto) {
        BalanceResponceDTO res = new BalanceResponceDTO();
        try {
            Web3j web3 = Web3j.build(new HttpService(dto.getNetwork()));
            EthBlockNumber blockNumber = web3.ethBlockNumber().send();
            EthGetBalance balance = web3.ethGetBalance(dto.getAddress(), DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send();
            BigInteger eth = balance.getBalance();
            res.setEther(Convert.fromWei(eth.toString(), Convert.Unit.ETHER).toString());
            if(!dto.getContract().isEmpty()) {
                ClientTransactionManager clientManager = new ClientTransactionManager(web3, dto.getAddress());
                ERC20Wrapper contract = ERC20Wrapper.load(dto.getContract(), web3, clientManager, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
                res.setToken(contract.balanceOf(dto.getAddress()).send().toString());
            }
        } catch (Exception ex) {
            log.info("balance of ", ex.getMessage());
        }
        
        return res;
    }
}
