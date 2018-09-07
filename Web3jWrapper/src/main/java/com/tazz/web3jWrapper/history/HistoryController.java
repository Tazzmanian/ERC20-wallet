/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tazz.web3jWrapper.history;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tazzmanian
 */
@RestController
public class HistoryController {
    private final HistoryService historyService;
    
    private HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }
    
    @PostMapping(value = "history")
    public List<HistoryResponseDTO> getHistory(@RequestBody HistoryRequestDTO dto) {
        return historyService.getHistory(dto);
    }
}
