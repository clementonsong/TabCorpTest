package com.clement.tabcorp.mservices.transaction.service;

import com.clement.tabcorp.common.dto.TransactionDTO;
import com.clement.tabcorp.mservices.transaction.model.Order;
import com.clement.tabcorp.mservices.transaction.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder_Success() {
        TransactionDTO dto = new TransactionDTO("CUST123", "PROD456", LocalDateTime.parse("2025-03-11T00:00:00"), 2);
        when(orderRepository.getNextOrderId()).thenReturn("TC11");

        orderService.createOrder(dto, 100.0, "Australia");

        verify(orderRepository, times(1)).saveOrder(any(Order.class));
    }
}
