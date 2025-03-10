package com.clement.tabcorp.mservices.customer.dto;

public class CustomerReportDTO {
    private String customerCode;
    private String name;
    private double transactionValue;

    public CustomerReportDTO(String customerCode, String name, double transactionValue) {
        this.customerCode = customerCode;
        this.name = name;
        this.transactionValue = transactionValue;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getName() {
        return name;
    }

    public double getTransactionValue() {
        return transactionValue;
    }
}