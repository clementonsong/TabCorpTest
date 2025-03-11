package com.clement.tabcorp.mservices.transaction.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetCustomerDetails_Success() {
        JsonNode mockResponse = mock(JsonNode.class);
        when(restTemplate.getForObject("http://localhost:8081/customers/CUST123", JsonNode.class))
                .thenReturn(mockResponse);

        Optional<JsonNode> result = customerService.getCustomerDetails("CUST123");

        assertTrue(result.isPresent());
        assertEquals(mockResponse, result.get());
    }

    @Test
    void testGetCustomerDetails_Failure() {
        when(restTemplate.getForObject("http://localhost:8081/customers/CUST123", JsonNode.class))
                .thenThrow(new RuntimeException("Customer not found"));

        Optional<JsonNode> result = customerService.getCustomerDetails("CUST123");

        assertFalse(result.isPresent());
    }
}
