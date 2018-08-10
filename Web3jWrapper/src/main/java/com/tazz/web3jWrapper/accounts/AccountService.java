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
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;

/**
 *
 * @author Tazzmanian
 */
@Slf4j
@Service
public class AccountService {
    
    public CreateResponseDTO createNewAccount(String password) {
        CreateResponseDTO dto = new CreateResponseDTO();
        try {
            String dir = WalletUtils.getDefaultKeyDirectory();
            Bip39Wallet wallet = WalletUtils.generateBip39Wallet(password, new File(dir));
//            String[] words = mnemonics.split(" ");
//            ArrayList<String> arr = new ArrayList(Arrays.stream(words).collect(Collectors.toList()));
            dto.setMnemonics(wallet.getMnemonic());
            File file = new File(dir + File.separator + wallet.getFilename());
            Credentials cred = WalletUtils.loadCredentials(password, file);
            dto.setHash(cred.getAddress());
            file.delete();
        } catch (Exception e) {
            log.info("create file" + e);
        }
        
        return dto;
    }

    CreateResponseDTO restore(RestoreRequestDTO dto) {
        Credentials cred = WalletUtils.loadBip39Credentials(dto.getPassword(), dto.getMnemonics());
        if(!WalletUtils.isValidAddress(cred.getAddress())) {
            return null;
        }
        CreateResponseDTO res = new CreateResponseDTO();
        res.setMnemonics(dto.getMnemonics());
        res.setHash(cred.getAddress());
        return res;
    }
}
