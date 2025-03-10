// OrderService.java - Handles Order Business Logic

package com.clement.tabcorp.mservices.transaction.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clement.tabcorp.common.dto.TransactionDTO;
import com.clement.tabcorp.mservices.transaction.model.Order;
import com.clement.tabcorp.mservices.transaction.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Create and save a new order
    public void createOrder(TransactionDTO dto, double totalCost, String customerLocation) { 
    	String customerCode = dto.getCustomerCode();
    	String productCode = dto.getProductCode();
    	int quantity = dto.getQuantity();
    	LocalDateTime date = dto.getTransactionTime();
        String nextOrderId = orderRepository.getNextOrderId(); // Generate next TC# ID
        Order newOrder = new Order(nextOrderId, customerCode, productCode, quantity, totalCost, customerLocation, date);
        orderRepository.saveOrder(newOrder); 
    }
}
