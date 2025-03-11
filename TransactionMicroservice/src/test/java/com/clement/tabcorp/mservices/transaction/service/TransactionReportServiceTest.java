package com.clement.tabcorp.mservices.transaction.service;

import com.clement.tabcorp.mservices.transaction.dto.TransactionReportDTO;
import com.clement.tabcorp.mservices.transaction.repository.TransactionReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionReportServiceTest {

    @Mock
    private TransactionReportRepository transactionReportRepository;

    @InjectMocks
    private TransactionReportService transactionReportService;

    @Test
    void testGetTransactionReportByLocation() {
        when(transactionReportRepository.countTransactionsByLocation("Australia")).thenReturn(5);
        when(transactionReportRepository.findTransactionsByLocation("Australia")).thenReturn(List.of());

        TransactionReportDTO result = transactionReportService.getTransactionReportByLocation("Australia");

        assertEquals(5, result.getTransactionCount());
        assertNotNull(result.getTransactions());
    }
}
