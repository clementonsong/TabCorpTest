// OrderRepository.java - Handles Database Operations for Orders

package com.clement.tabcorp.mservices.transaction.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clement.tabcorp.mservices.transaction.model.Order;

@Repository
public class OrderRepository {
	 private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Fetch latest Order_ID from the database
    public String getLastOrderId() {
        String sql = "SELECT Order_ID FROM orders ORDER BY Order_ID DESC LIMIT 1";
        try {
            String lastOrderId = jdbcTemplate.queryForObject(sql, String.class);
            return lastOrderId;
        } catch (Exception e) {
            return "TC0"; // Default if no orders exist - FOR A FRESH DATABASE ORDERS TABLE
        }
    }

    // Generate next Order_ID by incrementing the last Order_ID
    public String getNextOrderId() {
        String lastOrderId = getLastOrderId();
        int nextId = Integer.parseInt(lastOrderId.replace("TC", "")) + 1;
        return "TC" + nextId;
    }

    // Insert a new order into the database
    public boolean saveOrder(Order order) {
        String sql = "INSERT INTO orders (Order_ID, Customer_Code, Product_Code, Quantity, Transaction_Cost, Customer_Location, Transaction_Time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, order.getOrderId(), order.getCustomerCode(), order.getProductCode(), order.getQuantity(), order.getTxnCost(), order.getCustomerLocation(), order.getTransactionTime());
            return true;  // successful insertion
        } catch (Exception e) {
        	logger.error("There was an error when persisting the transaction");
            e.printStackTrace();
            return false; // Return false if insertion failed
        }
    }
}
