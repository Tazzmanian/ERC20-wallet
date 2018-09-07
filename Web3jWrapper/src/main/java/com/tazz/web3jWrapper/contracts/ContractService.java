/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.contracts;

import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 *
 * @author Tazzmanian
 */
@Slf4j
@Service
public class ContractService {

    public ContractData loadContract(LoadContractDTO dto) {
        ContractData data = new ContractData();
        
        Web3j web3 = Web3j.build(new HttpService(dto.getNetwork()));
                
        ClientTransactionManager clientManager = new ClientTransactionManager(web3, dto.getPublicAddress());
        ERC20Wrapper contract = ERC20Wrapper.load(dto.getContractAddress(), web3, clientManager, DefaultGasProvider.GAS_PRICE, Transfer.GAS_LIMIT);
        
        try {
            data.setTotalSupply(contract._totalSupply().send().toString());
            data.setName(contract.name().send());
            data.setSymbol(contract.symbol().send());
            data.setDecimals(contract.decimals().send().toString());
        } catch (Exception ex) {
            Logger.getLogger(ContractService.class.getName()).log(Level.SEVERE, null, ex);
            log.error(ContractService.class.getName() + ex);
        }       
        
        return data;
    }
    
}
