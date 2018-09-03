/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.send;

import com.tazz.web3jWrapper.accounts.CreateResponseDTO;
import com.tazz.web3jWrapper.contracts.ERC20Wrapper;
import java.io.File;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

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
        Credentials cred = WalletUtils.loadBip39Credentials(dto.getPassword(), dto.getMnemonics());
        Web3j web3 = Web3j.build(new HttpService(dto.getNetwork()));

        try{
            return Transfer.sendFunds(
            web3, cred, dto.getTo(),
            dto.getEthers(), Convert.Unit.ETHER)
            .send();            
        } catch(Exception e) {
            log.info("Send funds " + e.getMessage());
        }
        
        return null;
    }

    private TransactionReceipt sendToken(SendDTO dto) {
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
}
