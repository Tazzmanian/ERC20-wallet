/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.history;

import lombok.Data;

/**
 *
 * @author Tazzmanian
 */
@Data
public class HistoryResponseDTO {
    private String from;
    private String to;
    private String network;
    private String contract;
    private String amount;
    private String txHash;
    private String curruncy;
    private String nonce;
    private String blockNumber;
}
