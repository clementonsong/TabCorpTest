package com.clement.tabcorp.mservices.customer.controller;

import com.clement.tabcorp.mservices.customer.dto.CustomerReportDTO;
import com.clement.tabcorp.mservices.customer.service.CustomerReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/reports")
public class CustomerReportController {
    private final CustomerReportService customerReportService;

    public CustomerReportController(CustomerReportService customerReportService) {
        this.customerReportService = customerReportService;
    }

    @GetMapping("/total-cost")
    public List<CustomerReportDTO> getTotalTransactionCostPerCustomer() {
        return customerReportService.getTotalTransactionCostPerCustomer();
    }
}