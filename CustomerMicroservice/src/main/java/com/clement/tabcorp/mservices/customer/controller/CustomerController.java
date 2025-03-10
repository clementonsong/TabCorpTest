// CustomerMicroservice - CustomerDetails API Controller

package com.clement.tabcorp.mservices.customer.controller;

import com.clement.tabcorp.mservices.customer.model.Customer;
import com.clement.tabcorp.mservices.customer.service.CustomerDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @GetMapping("/{customerCode}")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable String customerCode) {
        logger.info("Fetching customer details for code: {}", customerCode);
        Optional<Customer> customer = customerDetailsService.getCustomerDetails(customerCode);

        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
