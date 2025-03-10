package com.clement.tabcorp.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customerCode;
    private String productCode;
    private LocalDateTime transactionTime;
    private int quantity;

    public TransactionDTO() {}

    public TransactionDTO(String customerCode, String productCode, LocalDateTime transactionTime, int quantity) {
        this.customerCode = customerCode;
        this.productCode = productCode;
        this.transactionTime = transactionTime;
        this.quantity = quantity;
    }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public LocalDateTime getTransactionTime() { return transactionTime; }
    public void setTransactionTime(LocalDateTime transactionTime) { this.transactionTime = transactionTime; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "customerCode=" + customerCode +
                ", productCode='" + productCode + '\'' +
                ", transactionTime=" + transactionTime +
                ", quantity=" + quantity +
                '}';
    }
}
