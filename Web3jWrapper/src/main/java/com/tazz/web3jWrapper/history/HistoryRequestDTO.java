/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.history;

import java.math.BigInteger;
import lombok.Data;

/**
 *
 * @author Tazzmanian
 */
@Data
public class HistoryRequestDTO {
    private String network;
    private String address;
    private BigInteger nonce;
    private BigInteger blockNumber;
}
