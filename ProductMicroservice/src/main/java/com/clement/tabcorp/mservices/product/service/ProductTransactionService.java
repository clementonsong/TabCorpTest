package com.clement.tabcorp.mservices.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductTransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateTransactionValue(String productCode, double transactionValue) {
        String sql = "UPDATE products SET Transaction_Value = Transaction_Value + ? WHERE Product_Code = ?";
        jdbcTemplate.update(sql, transactionValue, productCode);
    }
}
