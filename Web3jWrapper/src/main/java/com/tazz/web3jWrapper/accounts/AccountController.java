/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.accounts;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tazzmanian
 */
@RestController
@RequestMapping(value = "/accounts/")
public class AccountController {
    
    private final AccountService accountService;
    
    @Autowired
    private AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<String> createAccount(@RequestBody CreateDTO dto) {
        return accountService.createNewAccount(dto.getPassword()); 
    }
    
}
