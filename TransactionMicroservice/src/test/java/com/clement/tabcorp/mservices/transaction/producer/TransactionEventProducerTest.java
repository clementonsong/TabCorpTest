package com.clement.tabcorp.mservices.transaction.producer;

import com.clement.tabcorp.common.dto.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionEventProducerTest {

    @Mock
    private KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    @InjectMocks
    private TransactionEventProducer transactionEventProducer;

    @Test
    void testSendTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO("CUST123", "PROD456", LocalDateTime.parse("2025-03-11T00:00:00"), 2);

        transactionEventProducer.sendTransaction("transaction-events", transactionDTO);

        verify(kafkaTemplate, times(1)).send("transaction-events", transactionDTO);
    }
}
