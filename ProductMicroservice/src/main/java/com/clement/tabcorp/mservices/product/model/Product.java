package com.clement.tabcorp.mservices.product.model;

public class Product {
    private String productCode;
    private double cost;
    private String status;

    public Product(String productCode, double cost, String status) {
        this.productCode = productCode;
        this.cost = cost;
        this.status = status;
    }

    public String getProductCode() { return productCode; }
    public double getCost() { return cost; }
    public String getStatus() { return status; }
}
