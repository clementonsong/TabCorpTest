package com.clement.tabcorp.mservices.transaction.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.clement.tabcorp.common.dto.TransactionDTO;
import com.clement.tabcorp.mservices.transaction.producer.TransactionEventProducer;
import com.clement.tabcorp.mservices.transaction.service.OrderService;
import com.clement.tabcorp.mservices.transaction.validator.TransactionValidationResult;
import com.clement.tabcorp.mservices.transaction.validator.TransactionValidator;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionEventProducer transactionEventProducer;

    @Mock
    private TransactionValidator transactionValidator;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @Test
    void testCreateTransaction_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        when(transactionValidator.validate(any(TransactionDTO.class))).thenReturn(TransactionValidationResult.success(50.0, "Australia"));

        mockMvc.perform(post("/transactions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerCode\":\"CUST123\",\"productCode\":\"PROD456\",\"transactionTime\":\"2025-03-11T00:00:00\",\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Transaction accepted, saved, and event published."));

        verify(orderService, times(1)).createOrder(any(TransactionDTO.class), eq(100.0), eq("Australia"));
        verify(transactionEventProducer, times(1)).sendTransaction(eq("transaction-events"), any(TransactionDTO.class));
    }

    @Test
    void testCreateTransaction_Failure_InvalidData() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        when(transactionValidator.validate(any(TransactionDTO.class))).thenReturn(TransactionValidationResult.failure("Error: Invalid product"));

        mockMvc.perform(post("/transactions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerCode\":\"CUST123\",\"productCode\":\"PROD456\",\"transactionTime\":\"2025-03-11T00:00:00\",\"quantity\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Invalid product"));

        verify(orderService, never()).createOrder(any(), anyDouble(), anyString());
        verify(transactionEventProducer, never()).sendTransaction(anyString(), any());
    }
}
