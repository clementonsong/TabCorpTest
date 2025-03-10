package com.clement.tabcorp.mservices.transaction.model;

import java.time.LocalDateTime;

public class Order {
	private String orderId;
    private String customerCode;
    private String productCode;
    private int quantity;
    private double txnCost;
    private String customerLocation;
    private LocalDateTime transactionTime;

    public Order(String orderId, String customerCode, String productCode, int quantity, double totalCost, String customerLocation, LocalDateTime transactionTime) {
        this.orderId = orderId;
        this.customerCode = customerCode;
        this.productCode = productCode;
        this.quantity = quantity;
        this.txnCost = totalCost;
        this.customerLocation = customerLocation;
        this.transactionTime = transactionTime;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerCode() { return customerCode; }
    public String getProductCode() { return productCode; }
    public int getQuantity() { return quantity; }
    public double getTxnCost() { return txnCost; }
    public String getCustomerLocation() { return customerLocation; }
    public LocalDateTime getTransactionTime() { return transactionTime; }
    
    @Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customerCode=" + customerCode + ", productCode=" + productCode
				+ ", quantity=" + quantity + ", txnCost=" + txnCost + ", customerLocation=" + customerLocation
				+ ", transactionTime=" + transactionTime + "]";
	}
}
