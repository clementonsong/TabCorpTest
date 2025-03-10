package com.clement.tabcorp.mservices.customer.consumer;

import com.clement.tabcorp.common.dto.TransactionDTO;
import com.clement.tabcorp.mservices.customer.service.CustomerTransactionService;
import com.clement.tabcorp.mservices.customer.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TransactionEventConsumer.class);

    @Autowired
    private CustomerTransactionService customerTransactionService;

    @Autowired
    private ProductService productService;

    @KafkaListener(topics = "transaction-events", groupId = "customer-group", containerFactory = "transactionKafkaListenerContainerFactory")
    public void consumeTransaction(TransactionDTO transaction) {
        logger.info("Received transaction in CustomerMicroservice: {}", transaction);

        Optional<Double> productCost = productService.getProductCost(transaction.getProductCode());

        if (productCost.isPresent()) {
            double totalTransactionValue = transaction.getQuantity() * productCost.get();
            customerTransactionService.updateTransactionValue(transaction.getCustomerCode(), totalTransactionValue);
            logger.info("Updated Customer Transaction Value successfully.");
        } else {
            logger.error("Failed to fetch product cost for productCode: {}", transaction.getProductCode());
        }
    }
}
