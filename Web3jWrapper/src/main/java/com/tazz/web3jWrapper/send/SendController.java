/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.send;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author Tazzmanian
 */
@RestController
//@RequestMapping(value = "/send/")
public class SendController {
    private final SendService sendService;
    
    private SendController(SendService sendService) {
        this.sendService = sendService;
    }
    
    @PostMapping(value = "/send")
    public TransactionReceipt send(@RequestBody SendDTO dto) {
        return sendService.send(dto);
    }
}
