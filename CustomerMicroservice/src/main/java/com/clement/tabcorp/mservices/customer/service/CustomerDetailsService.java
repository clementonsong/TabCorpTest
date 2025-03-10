// CustomerMicroservice - CustomerDetails Service

package com.clement.tabcorp.mservices.customer.service;

import com.clement.tabcorp.mservices.customer.model.Customer;
import com.clement.tabcorp.mservices.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> getCustomerDetails(String customerCode) {
        return customerRepository.findByCustomerCode(customerCode);
    }
}
