package com.clement.tabcorp.mservices.product.controller;

import com.clement.tabcorp.mservices.product.model.Product;
import com.clement.tabcorp.mservices.product.service.ProductDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductDetailsService productDetailsService;

    @GetMapping("/{productCode}")
    public ResponseEntity<Product> getProductDetails(@PathVariable String productCode) {
        logger.info("Fetching product details for code: {}", productCode);
        Optional<Product> product = productDetailsService.getProductDetails(productCode);

        return product.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
