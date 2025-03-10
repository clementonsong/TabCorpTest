package com.clement.tabcorp.mservices.product.repository;

import com.clement.tabcorp.mservices.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Product> findByProductCode(String productCode) {
        String sql = "SELECT product_code, cost, status FROM products WHERE product_code  = ?";
        
        try {
            Product product = jdbcTemplate.queryForObject(sql, new ProductRowMapper(), productCode);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty(); // Return empty if no product found
        }
    }

    // RowMapper to map SQL results to Product object
    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                rs.getString("product_code"),
                rs.getDouble("cost"),
                rs.getString("status")
            );
        }
    }
}
