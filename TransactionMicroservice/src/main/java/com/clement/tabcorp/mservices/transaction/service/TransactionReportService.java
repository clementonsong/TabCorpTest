package com.clement.tabcorp.mservices.transaction.service;

import com.clement.tabcorp.mservices.transaction.dto.TransactionReportDTO;
import com.clement.tabcorp.mservices.transaction.repository.TransactionReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionReportService {
    
    @Autowired
    private TransactionReportRepository transactionReportRepository;

    public TransactionReportDTO getTransactionReportByLocation(String location) {
        int transactionCount = transactionReportRepository.countTransactionsByLocation(location);
        List<TransactionReportDTO.TransactionDetail> transactions = transactionReportRepository.findTransactionsByLocation(location);
        
        return new TransactionReportDTO(transactionCount, transactions);
    }
}