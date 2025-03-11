package com.clement.tabcorp.mservices.transaction.repository;

import com.clement.tabcorp.mservices.transaction.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrderRepository orderRepository;

    @Test
    void testGetLastOrderId_WhenOrdersExist() {
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class))).thenReturn("TC10");

        String lastOrderId = orderRepository.getLastOrderId();

        assertEquals("TC10", lastOrderId);
    }

    @Test
    void testGetLastOrderId_WhenNoOrdersExist() {
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException());

        String lastOrderId = orderRepository.getLastOrderId();

        assertEquals("TC0", lastOrderId);
    }

    @Test
    void testGetNextOrderId() {
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class))).thenReturn("TC10");

        String nextOrderId = orderRepository.getNextOrderId();

        assertEquals("TC11", nextOrderId);
    }

    @Test
    void testSaveOrder_Success() {
        Order order = new Order("TC1", "CUST123", "PROD456", 2, 100.0, "Australia", null);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        boolean result = orderRepository.saveOrder(order);

        assertTrue(result);
    }

    @Test
    void testSaveOrder_Failure() {
        Order order = new Order("TC1", "CUST123", "PROD456", 2, 100.0, "Australia", null);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any())).thenThrow(new RuntimeException());

        boolean result = orderRepository.saveOrder(order);

        assertFalse(result);
    }
}
