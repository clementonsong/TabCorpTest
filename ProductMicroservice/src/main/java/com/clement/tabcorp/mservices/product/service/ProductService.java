package com.clement.tabcorp.mservices.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PRODUCT_API_URL = "http://localhost:8082/products/";

    public Optional<Double> getProductCost(String productCode) {
        try {
            JsonNode productData = restTemplate.getForObject(PRODUCT_API_URL + productCode, JsonNode.class);
            if (productData != null && productData.has("cost")) {
                return Optional.of(productData.get("cost").asDouble());
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }
}
