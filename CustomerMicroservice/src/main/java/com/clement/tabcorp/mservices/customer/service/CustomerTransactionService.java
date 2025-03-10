package com.clement.tabcorp.mservices.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerTransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateTransactionValue(String customerCode, double transactionValue) {
        String sql = "UPDATE customers SET Transaction_Value = Transaction_Value + ? WHERE Customer_Code = ?";
        jdbcTemplate.update(sql, transactionValue, customerCode);
    }
}
