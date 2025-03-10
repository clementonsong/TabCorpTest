package com.clement.tabcorp.mservices.product.controller;

import com.clement.tabcorp.mservices.product.dto.ProductReportDTO;
import com.clement.tabcorp.mservices.product.service.ProductReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/reports")
public class ProductReportController {
    private final ProductReportService productReportService;

    public ProductReportController(ProductReportService productReportService) {
        this.productReportService = productReportService;
    }

    @GetMapping("/total-cost")
    public List<ProductReportDTO> getTotalTransactionCostPerProduct() {
        return productReportService.getTotalTransactionCostPerProduct();
    }
}
