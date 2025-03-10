package com.clement.tabcorp.mservices.transaction.dto;

import java.util.List;

public class TransactionReportDTO {
    private int transactionCount;
    private List<TransactionDetail> transactions;

    public TransactionReportDTO(int transactionCount, List<TransactionDetail> transactions) {
        this.transactionCount = transactionCount;
        this.transactions = transactions;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public List<TransactionDetail> getTransactions() {
        return transactions;
    }

    public static class TransactionDetail {
        private String orderId;
        private double transactionCost;
        private String customerCode;

        public TransactionDetail(String orderId, double transactionCost, String customerCode) {
            this.orderId = orderId;
            this.transactionCost = transactionCost;
            this.customerCode = customerCode;
        }

        public String getOrderId() {
            return orderId;
        }

        public double getTransactionCost() {
            return transactionCost;
        }

        public String getCustomerCode() {
            return customerCode;
        }
    }
}