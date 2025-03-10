package com.clement.tabcorp.mservices.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clement.tabcorp.mservices.transaction.dto.TransactionReportDTO;
import com.clement.tabcorp.mservices.transaction.service.TransactionReportService;

@RestController
@RequestMapping("/api/transactions/reports")
public class TransactionReportController {
    
    @Autowired
    private TransactionReportService transactionReportService;

    @GetMapping("/count/{location}")
    public TransactionReportDTO getTransactionReportByLocation(@PathVariable String location) {
        return transactionReportService.getTransactionReportByLocation(location);
    }
}