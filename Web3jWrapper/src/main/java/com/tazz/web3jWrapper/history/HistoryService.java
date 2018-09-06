/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.history;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

/**
 *
 * @author Tazzmanian
 */
@Slf4j
@Service
public class HistoryService {

    public List<HistoryResponseDTO> getHistory(HistoryRequestDTO dto) {
        List<HistoryResponseDTO> res = new ArrayList<>();
        try {
            Web3j web3 = Web3j.build(new HttpService(dto.getNetwork()));
            DefaultBlockParameter start = DefaultBlockParameter.valueOf(dto.getBlockNumber());
            Request<?, EthBlock> request = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false);
            DefaultBlockParameter end = DefaultBlockParameter.valueOf(web3.ethBlockNumber().send().getBlockNumber());
            
            web3.catchUpToLatestTransactionObservable(start).subscribe(tx -> {
                log.info("===============================");
                log.info("block hash " + tx.getBlockHash());
                log.info("block number row " + tx.getBlockNumberRaw());
                log.info("creates " + tx.getCreates());
                log.info("from " + tx.getFrom());
                log.info("gas price raw " + tx.getGasPriceRaw());
                log.info("gas row " + tx.getGasRaw());
                log.info("hash " + tx.getHash());
                log.info("input " + tx.getInput());
                log.info("nonce row " + tx.getNonceRaw());
                log.info("public key " + tx.getPublicKey());
                log.info("r " + tx.getR());
                log.info("raw " + tx.getRaw());
                log.info("s " + tx.getS());
                log.info("to " + tx.getTo());
                log.info("tx index raw " + tx.getTransactionIndexRaw());
                log.info("value raw " + tx.getValueRaw());
                log.info("block number " + tx.getBlockNumber().toString());
                log.info("chain id " + tx.getChainId().toString());
                log.info("gas " + tx.getGas().toString());
                log.info("gas price " + tx.getGasPrice().toString());
                log.info("nonce " + tx.getNonce().toString());
                log.info("v " + BigInteger.valueOf(tx.getV()).toString());
                log.info("value " + tx.getValue().toString());
            });
            
//            web3.replayBlocksObservable(start, end, false).subscribe(b -> {
//                log.info("===============================");
//                log.info("rpc " + b.getJsonrpc());
//                log.info("raw resp " + b.getRawResponse());
//                log.info("raw id " + b.getId());
//                log.info("error " + b.getError().getData());
//                log.info("error " + b.getError().getMessage());
//                log.info("error " + b.getError().getCode());
//                log.info("autor " + b.getBlock().getAuthor());
//                log.info("diff raw " + b.getBlock().getDifficultyRaw());
//                log.info("get extra data " + b.getBlock().getExtraData());
//                log.info("gas limit raw " + b.getBlock().getGasLimitRaw());
//                log.info("gas used raw " + b.getBlock().getGasUsedRaw());
//                log.info("hash " + b.getBlock().getHash());
//                log.info("bloom " + b.getBlock().getLogsBloom());
//                log.info("miner " + b.getBlock().getMiner());
//                log.info("mix hash " + b.getBlock().getMixHash());
//                log.info("nonce raw " + b.getBlock().getNonceRaw());
//                log.info("number raw " + b.getBlock().getNumberRaw());
//                log.info("parent " + b.getBlock().getParentHash());
//                log.info("receopts " + b.getBlock().getReceiptsRoot());
//                log.info("sha3 " + b.getBlock().getSha3Uncles());
//                log.info("size raw " + b.getBlock().getSizeRaw());
//                log.info("state root " + b.getBlock().getStateRoot());
//                log.info("timeraw " + b.getBlock().getTimestampRaw());
//                log.info("diff raw " + b.getBlock().getTotalDifficultyRaw());
//                log.info("tranroot " + b.getBlock().getTransactionsRoot());
//                log.info("diffic " + b.getBlock().getDifficulty());
//                log.info("gas limit " + b.getBlock().getGasLimit());
//                log.info("gas used " + b.getBlock().getGasUsed());
//                log.info("nonce " + b.getBlock().getNonce());
//                log.info("number " + b.getBlock().getNumber());
//                log.info("fields " + b.getBlock().getSealFields());
//                log.info("time " + b.getBlock().getTimestamp());
//                log.info("diff " + b.getBlock().getTotalDifficulty());
//                log.info("uncle " + b.getBlock().getUncles());
//                log.info("size " + b.getBlock().getTransactions().size());
//                b.getBlock().getTransactions().forEach(x -> {
//                    log.info("x " + x.get().toString());
//                });
//                
//                log.info("-------- ");
//                log.info("autor " + b.getResult().getAuthor());
//                log.info("diff raw " + b.getResult().getDifficultyRaw());
//                log.info("get extra data " + b.getResult().getExtraData());
//                log.info("gas limit raw " + b.getResult().getGasLimitRaw());
//                log.info("gas used raw " + b.getResult().getGasUsedRaw());
//                log.info("hash " + b.getResult().getHash());
//                log.info("bloom " + b.getResult().getLogsBloom());
//                log.info("miner " + b.getResult().getMiner());
//                log.info("mix hash " + b.getResult().getMixHash());
//                log.info("nonce raw " + b.getResult().getNonceRaw());
//                log.info("number raw " + b.getResult().getNumberRaw());
//                log.info("parent " + b.getResult().getParentHash());
//                log.info("receopts " + b.getResult().getReceiptsRoot());
//                log.info("sha3 " + b.getResult().getSha3Uncles());
//                log.info("size raw " + b.getResult().getSizeRaw());
//                log.info("state root " + b.getResult().getStateRoot());
//                log.info("timeraw " + b.getResult().getTimestampRaw());
//                log.info("diff raw " + b.getResult().getTotalDifficultyRaw());
//                log.info("tranroot " + b.getResult().getTransactionsRoot());
//                log.info("diffic " + b.getResult().getDifficulty());
//                log.info("gas limit " + b.getResult().getGasLimit());
//                log.info("gas used " + b.getResult().getGasUsed());
//                log.info("nonce " + b.getResult().getNonce());
//                log.info("number " + b.getResult().getNumber());
//                log.info("fields " + b.getResult().getSealFields());
//                log.info("time " + b.getResult().getTimestamp());
//                log.info("diff " + b.getResult().getTotalDifficulty());
//                log.info("uncle " + b.getResult().getUncles());
//                log.info("size " + b.getResult().getTransactions().size());
//                b.getResult().getTransactions().forEach(x -> {
//                    log.info("x " + x.get().toString());
//                });
//            });
//
//            web3.replayTransactionsObservable(start, end).subscribe(tx -> {
//                log.info("===============================");
//                log.info("block hash " + tx.getBlockHash());
//                log.info("block number row " + tx.getBlockNumberRaw());
//                log.info("creates " + tx.getCreates());
//                log.info("from " + tx.getFrom());
//                log.info("gas price raw " + tx.getGasPriceRaw());
//                log.info("gas row " + tx.getGasRaw());
//                log.info("hash " + tx.getHash());
//                log.info("input " + tx.getInput());
//                log.info("nonce row " + tx.getNonceRaw());
//                log.info("public key " + tx.getPublicKey());
//                log.info("r " + tx.getR());
//                log.info("raw " + tx.getRaw());
//                log.info("s " + tx.getS());
//                log.info("to " + tx.getTo());
//                log.info("tx index raw " + tx.getTransactionIndexRaw());
//                log.info("value raw " + tx.getValueRaw());
//                log.info("block number " + tx.getBlockNumber().toString());
//                log.info("chain id " + tx.getChainId().toString());
//                log.info("gas " + tx.getGas().toString());
//                log.info("gas price " + tx.getGasPrice().toString());
//                log.info("nonce " + tx.getNonce().toString());
//                log.info("v " + BigInteger.valueOf(tx.getV()).toString());
//                log.info("value " + tx.getValue().toString());
//            });
            
            
        } catch (Exception ex) {
            log.info("History: " + ex.getMessage());
        }
        return res;
    }
    
}
