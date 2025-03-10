package com.clement.tabcorp.mservices.product.repository;

import com.clement.tabcorp.mservices.product.dto.ProductReportDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductReportDTO> getTotalTransactionCostPerProduct() {
        String sql = "SELECT Product_Code, Transaction_Value FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProductReportDTO(
                rs.getString("Product_Code"),
                rs.getDouble("Transaction_Value")
        ));
    }
}