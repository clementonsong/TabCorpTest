package com.clement.tabcorp.mservices.transaction.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private RestTemplate restTemplate;

    public Optional<JsonNode> getProductDetails(String productCode) {
        String url = "http://localhost:8082/products/" + productCode; // Assuming ProductMicroservice runs on 8082
        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            logger.error("Error fetching product details for code: {}", productCode, e);
            return Optional.empty();
        }
    }
}
