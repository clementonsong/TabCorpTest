// TransactionMicroservice - CustomerService for Fetching Customer Details

package com.clement.tabcorp.mservices.transaction.service;

import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CustomerService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String CUSTOMER_API_URL = "http://localhost:8081/customers/";

    public Optional<JsonNode> getCustomerDetails(String customerCode) {
        try {
            JsonNode response = restTemplate.getForObject(CUSTOMER_API_URL + customerCode, JsonNode.class);
            return Optional.ofNullable(response);
        } catch (Exception e) {
        	logger.error("Error fetching customer details for code: {}", customerCode, e);
            return Optional.empty(); // Return empty if customer is not found
        }
    }
}
