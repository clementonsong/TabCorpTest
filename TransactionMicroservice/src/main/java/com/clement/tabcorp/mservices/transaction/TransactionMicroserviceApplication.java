package com.clement.tabcorp.mservices.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * THE KEY CLASS FOR TAB CORP CUSTOMER ORDER PROCESSING - 
 * THIS MICRO SHOULD BE SCALLED UP HORIZANTALY ACROSS MULTIPLE CONTAINERS FOR HIGH AVAILABILITY
 */
@SpringBootApplication
public class TransactionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionMicroserviceApplication.class, args);
	}

}
