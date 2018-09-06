/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.send;

import lombok.Data;

/**
 *
 * @author Tazzmanian
 */
@Data
public class BalanceResponceDTO {
    private String ether;
    private String token;
    
    public BalanceResponceDTO() {
        ether = "N/A";
        token = "N/A";
    }
}
