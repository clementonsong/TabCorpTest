package com.clement.tabcorp.mservices.product.service;

import com.clement.tabcorp.mservices.product.dto.ProductReportDTO;
import com.clement.tabcorp.mservices.product.repository.ProductReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReportService {
    private final ProductReportRepository productReportRepository;

    public ProductReportService(ProductReportRepository productReportRepository) {
        this.productReportRepository = productReportRepository;
    }

    public List<ProductReportDTO> getTotalTransactionCostPerProduct() {
        return productReportRepository.getTotalTransactionCostPerProduct();
    }
}
