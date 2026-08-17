package com.retail.rewards.model;

import com.retail.rewards.entity.Customer;

public class CustomerInfo {
    private String customerId;
    private String customerName;
    private String email;

    public CustomerInfo(String customerId, String customerName, String email) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
    }

    public CustomerInfo(Customer customer) {
        this(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail());
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
