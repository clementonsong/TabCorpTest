package com.clement.tabcorp.mservices.transaction.producer;

import com.clement.tabcorp.common.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(TransactionEventProducer.class);
    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public TransactionEventProducer(KafkaTemplate<String, TransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(String topic, TransactionDTO transaction) {
        logger.info("Sending transaction event to topic {}: {}", topic, transaction);
        kafkaTemplate.send(topic, transaction);
    }
}
