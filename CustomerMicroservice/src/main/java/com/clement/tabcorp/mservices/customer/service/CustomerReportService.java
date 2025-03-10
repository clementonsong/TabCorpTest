package com.clement.tabcorp.mservices.customer.service;

import com.clement.tabcorp.mservices.customer.dto.CustomerReportDTO;
import com.clement.tabcorp.mservices.customer.repository.CustomerReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerReportService {
    private final CustomerReportRepository customerReportRepository;

    public CustomerReportService(CustomerReportRepository customerReportRepository) {
        this.customerReportRepository = customerReportRepository;
    }

    public List<CustomerReportDTO> getTotalTransactionCostPerCustomer() {
        return customerReportRepository.getTotalTransactionCostPerCustomer();
    }
}
