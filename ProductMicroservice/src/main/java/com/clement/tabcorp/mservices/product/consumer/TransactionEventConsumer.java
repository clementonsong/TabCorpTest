package com.clement.tabcorp.mservices.product.consumer;

import com.clement.tabcorp.common.dto.TransactionDTO;
import com.clement.tabcorp.mservices.product.service.ProductTransactionService;
import com.clement.tabcorp.mservices.product.service.ProductService;
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
    private ProductTransactionService productTransactionService;

    @Autowired
    private ProductService productService;

    @KafkaListener(topics = "transaction-events", groupId = "product-group", containerFactory = "transactionKafkaListenerContainerFactory")
    public void consumeTransaction(TransactionDTO transaction) {
        logger.info("Received transaction in ProductMicroservice: {}", transaction);

        Optional<Double> productCost = productService.getProductCost(transaction.getProductCode());

        if (productCost.isPresent()) {
            double totalTransactionValue = transaction.getQuantity() * productCost.get();
            productTransactionService.updateTransactionValue(transaction.getProductCode(), totalTransactionValue);
            logger.info("Updated Product Transaction Value successfully.");
        } else {
            logger.error("Failed to fetch product cost for productCode: {}", transaction.getProductCode());
        }
    }
}
