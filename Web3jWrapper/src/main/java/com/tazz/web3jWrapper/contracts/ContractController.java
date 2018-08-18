/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.contracts;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tazzmanian
 */
@RestController
@RequestMapping(value = "/contracts/")
public class ContractController {
    
    private final ContractService contractService;
    
    private ContractController(ContractService contractService) {
        this.contractService = contractService;
    }
    
    @PostMapping(value = "load")
    public ContractData loadContract(@RequestBody LoadContractDTO dto) {
        return contractService.loadContract(dto);
    }
}
