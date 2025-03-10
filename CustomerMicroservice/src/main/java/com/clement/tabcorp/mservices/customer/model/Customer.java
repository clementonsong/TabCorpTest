package com.clement.tabcorp.mservices.customer.model;

public class Customer {
    private String customerCode;
    private String firstName;
    private String lastName;
    private String email;
    private String location;

    public Customer(String customerCode, String firstName, String lastName, String email, String location) {
        this.customerCode = customerCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
    }

    public String getCustomerCode() { return customerCode; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getLocation() { return location; }
}
