/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.contracts;

import java.math.BigInteger;
import lombok.Data;

/**
 *
 * @author Tazzmanian
 */
@Data
class ContractData {
    private String symbol;
    private String name;
    private String totalSupply;
    private String decimals;
}
