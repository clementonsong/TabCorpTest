// TransactionValidator.java - Handles Validation and Returns Product Cost

package com.clement.tabcorp.mservices.transaction.validator;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clement.tabcorp.common.dto.TransactionDTO;
import com.clement.tabcorp.mservices.transaction.service.CustomerService;
import com.clement.tabcorp.mservices.transaction.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;

/**#################### THIS IS THE MOST IMPORTANT CLASS IN THIS ECO-SYSTEM #####################
 * This class is responsible for directly validating the request-data passed by CALLING API
 * ********VALIDATES the input data and passes error if any to the caller meaningfully***********
 */
@Component
public class TransactionValidator {
    private static final Logger logger = LoggerFactory.getLogger(TransactionValidator.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    /**Validates the JSON request - And also does the IMPORTANT job of capturing response data for orchestration of API data
     * @param transaction - INCOMING REQUEST JSON DTO
     * @return 
     */
    public TransactionValidationResult validate(TransactionDTO transaction) {
        try {
            if (transaction.getTransactionTime() == null) {
                return TransactionValidationResult.failure("Transaction time is required.");
            }
            if (transaction.getTransactionTime().toLocalDate().isBefore(LocalDate.now())) {
                return TransactionValidationResult.failure("Transaction time cannot be in the past.");
            }
            if (transaction.getQuantity() <= 0) {
                return TransactionValidationResult.failure("Transaction quantity must be at least 1.");
            }

            // Validate customer existence
            Optional<JsonNode> customerJson = customerService.getCustomerDetails(transaction.getCustomerCode());
            if (customerJson.isEmpty()) {
                return TransactionValidationResult.failure("Invalid customer code.");
            }
            
            JsonNode customerData = customerJson.get();
            String custLocation = (customerData.get("location").asText().isBlank())? "UNKNOWN" : customerData.get("location").asText();
            

            // Validate product status and fetch product cost
            Optional<JsonNode> productJson = productService.getProductDetails(transaction.getProductCode());
            if (productJson.isEmpty()) {
                return TransactionValidationResult.failure("Invalid product code.");
            }

            JsonNode productData = productJson.get();
            String status = productData.get("status").asText();
            if ("inactive".equalsIgnoreCase(status)) {
                return TransactionValidationResult.failure("PRODUCT INACTIVE");
            }

            double productCost = productData.get("cost").asDouble();
            double transactionCost = transaction.getQuantity() * productCost;

            if (transactionCost > 5000) {
                return TransactionValidationResult.failure("Total cost of transaction must not exceed 5000.");
            }

            // Return successful validation with product cost
            logger.info("TxnValidator(): Details of order: "+custLocation+" : "+productCost);
            return TransactionValidationResult.success(productCost,custLocation);

        } catch (Exception e) {
            logger.error("Error in Validator: " + e);
            e.printStackTrace();
            return TransactionValidationResult.failure("Unexpected error occurred during validation of request-data.");
        }
    }
}
