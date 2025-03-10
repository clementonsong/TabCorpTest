package com.clement.tabcorp.mservices.customer.repository;

import com.clement.tabcorp.mservices.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Customer> findByCustomerCode(String customerCode) {
        String sql = "SELECT Customer_Code, First_Name, Last_Name, Email, Location FROM customers WHERE Customer_Code = ?";
        
        try {
            Customer customer = jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), customerCode);
            return Optional.ofNullable(customer);
        } catch (Exception e) {
            return Optional.empty(); // Return empty if no customer found
        }
    }

    private static class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(
                rs.getString("Customer_Code"),
                rs.getString("First_Name"),
                rs.getString("Last_Name"),
                rs.getString("Email"),
                rs.getString("Location")
            );
        }
    }
}
