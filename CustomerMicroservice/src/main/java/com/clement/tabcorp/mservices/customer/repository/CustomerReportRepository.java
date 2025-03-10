package com.clement.tabcorp.mservices.customer.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clement.tabcorp.mservices.customer.dto.CustomerReportDTO;

@Repository
public class CustomerReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomerReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerReportDTO> getTotalTransactionCostPerCustomer() {
        String sql = "SELECT Customer_Code, CONCAT(First_Name, ' ', Last_Name) AS Name, Transaction_Value FROM customers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CustomerReportDTO(
                rs.getString("Customer_Code"),
                rs.getString("Name"),
                rs.getDouble("Transaction_Value")
        ));
    }
}
