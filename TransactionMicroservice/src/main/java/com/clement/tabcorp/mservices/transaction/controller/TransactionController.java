// TransactionController - Calls Validator and Saves Orders

package com.clement.tabcorp.mservices.transaction.controller;

import com.clement.tabcorp.common.dto.TransactionDTO;
import com.clement.tabcorp.mservices.transaction.producer.TransactionEventProducer;
import com.clement.tabcorp.mservices.transaction.service.OrderService;
import com.clement.tabcorp.mservices.transaction.validator.TransactionValidator;
import com.clement.tabcorp.mservices.transaction.validator.TransactionValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This Controller gets the input from transactions posted from the front-end (API Requests) and saves txn data for 
 * validation, processing & reporting 
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionEventProducer transactionEventProducer;

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        logger.info("Received transaction request: {}", transactionDTO);

        // Validate transaction and get product cost
        TransactionValidationResult validationResult = transactionValidator.validate(transactionDTO);
        if (!validationResult.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult.getErrorMessage());
        }
        
        // Aggregate data from validator's API calls - Save transaction into Orders table
        orderService.createOrder(transactionDTO, validationResult.getProductCost() * transactionDTO.getQuantity(), validationResult.getCustomerLocation()); 

        // Publish transaction event to Kafka for persisting in Customer & Product micros for report APIs 
        transactionEventProducer.sendTransaction("transaction-events", transactionDTO);
        logger.info("Transaction event published successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction accepted, saved, and event published.");
    }
}
