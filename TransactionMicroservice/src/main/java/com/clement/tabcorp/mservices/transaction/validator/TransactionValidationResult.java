package com.clement.tabcorp.mservices.transaction.validator;

@FunctionalInterface
public interface TransactionValidationResult {
    boolean isValid();
    default String getErrorMessage() { return null; }
    default double getProductCost() { return 0; }
    default String getCustomerLocation() { return ""; }

    // Updated success method
    static TransactionValidationResult success(double productCost, String custLocation) {
        return new TransactionValidationResult() {
            @Override public boolean isValid() { return true; }
            @Override public double getProductCost() { return productCost; }
            @Override public String getCustomerLocation() { return custLocation; }
        };
    }

    static TransactionValidationResult failure(String errorMessage) {
        return new TransactionValidationResult() {
            @Override public boolean isValid() { return false; }
            @Override public String getErrorMessage() { return errorMessage; }
            @Override public double getProductCost() { return 0; }  // No product cost on failure
        };
    }
}
