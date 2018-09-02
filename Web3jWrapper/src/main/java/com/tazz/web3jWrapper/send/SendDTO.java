/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.send;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.Data;
import org.web3j.utils.Convert;

/**
 *
 * @author Tazzmanian
 */
@Data
public class SendDTO {
    private String to;
    private BigDecimal ethers;
    private BigInteger tokens;
    private String network;
    private String mnemonics;
    private String password;
    private String contractAddress;
}
