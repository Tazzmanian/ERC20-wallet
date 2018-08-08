/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.accounts;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.WalletUtils;

/**
 *
 * @author Tazzmanian
 */
@Slf4j
@Service
public class AccountService {
    
    public ArrayList<String> createNewAccount(String password) {
        try {
            Bip39Wallet wallet = WalletUtils.generateBip39Wallet(password, new File(""));
            String mnemonics = wallet.getMnemonic();
            String[] words = mnemonics.split(" ");
            ArrayList<String> arr = new ArrayList(Arrays.stream(words).collect(Collectors.toList()));
            return arr;
        } catch (Exception e) {
            log.info("create file" + e);
        }
        
        return new ArrayList<String>();
    }
}
