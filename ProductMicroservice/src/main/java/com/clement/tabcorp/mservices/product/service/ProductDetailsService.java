package com.clement.tabcorp.mservices.product.service;

import com.clement.tabcorp.mservices.product.model.Product;
import com.clement.tabcorp.mservices.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailsService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> getProductDetails(String productCode) {
        return productRepository.findByProductCode(productCode);
    }
}
