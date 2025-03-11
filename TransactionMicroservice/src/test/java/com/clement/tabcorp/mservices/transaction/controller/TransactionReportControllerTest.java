package com.clement.tabcorp.mservices.transaction.controller;

import com.clement.tabcorp.mservices.transaction.dto.TransactionReportDTO;
import com.clement.tabcorp.mservices.transaction.service.TransactionReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransactionReportControllerTest {

    @Mock
    private TransactionReportService transactionReportService;

    @InjectMocks
    private TransactionReportController transactionReportController;

    private MockMvc mockMvc;

    @Test
    void testGetTransactionReportByLocation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionReportController).build();
        TransactionReportDTO reportDTO = new TransactionReportDTO(5, null);

        when(transactionReportService.getTransactionReportByLocation("Australia")).thenReturn(reportDTO);

        mockMvc.perform(get("/api/transactions/reports/count/Australia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionCount").value(5));
    }
}
