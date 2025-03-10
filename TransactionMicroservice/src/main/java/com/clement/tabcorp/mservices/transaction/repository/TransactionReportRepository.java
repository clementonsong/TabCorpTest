package com.clement.tabcorp.mservices.transaction.repository;

import com.clement.tabcorp.mservices.transaction.dto.TransactionReportDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionReportRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public TransactionReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int countTransactionsByLocation(String location) {
        String sql = "SELECT COUNT(*) FROM orders WHERE Customer_Location = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, location);
    }

    public List<TransactionReportDTO.TransactionDetail> findTransactionsByLocation(String location) {
        String sql = "SELECT Order_ID, Transaction_Cost, Customer_Code FROM orders WHERE Customer_Location = ?";
        
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TransactionReportDTO.TransactionDetail(
            rs.getString("Order_ID"),
            rs.getDouble("Transaction_Cost"),
            rs.getString("Customer_Code")
        ), location);
    }
}