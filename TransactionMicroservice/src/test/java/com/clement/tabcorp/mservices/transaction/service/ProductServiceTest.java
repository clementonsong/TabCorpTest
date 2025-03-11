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
class ProductServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetProductDetails_Success() {
        JsonNode mockResponse = mock(JsonNode.class);
        when(restTemplate.getForObject("http://localhost:8082/products/PROD123", JsonNode.class))
                .thenReturn(mockResponse);

        Optional<JsonNode> result = productService.getProductDetails("PROD123");

        assertTrue(result.isPresent());
        assertEquals(mockResponse, result.get());
    }

    @Test
    void testGetProductDetails_Failure() {
        when(restTemplate.getForObject("http://localhost:8082/products/PROD123", JsonNode.class))
                .thenThrow(new RuntimeException("Product not found"));

        Optional<JsonNode> result = productService.getProductDetails("PROD123");

        assertFalse(result.isPresent());
    }
}
