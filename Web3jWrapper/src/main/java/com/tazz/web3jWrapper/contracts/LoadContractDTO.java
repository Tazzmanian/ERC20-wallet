/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.contracts;

import lombok.Data;

/**
 *
 * @author Tazzmanian
 */
@Data
class LoadContractDTO {
    private String contractAddress;
    private String network;
    private String publicAddress;
}
