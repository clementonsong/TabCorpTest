package com.clement.tabcorp.mservices.product.dto;

public class ProductReportDTO {
    private String productCode;
    private double transactionValue;

    public ProductReportDTO(String productCode, double transactionValue) {
        this.productCode = productCode;
        this.transactionValue = transactionValue;
    }

    public String getProductCode() {
        return productCode;
    }

    public double getTransactionValue() {
        return transactionValue;
    }
}