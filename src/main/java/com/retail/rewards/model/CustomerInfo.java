package com.retail.rewards.model;

import com.retail.rewards.entity.Customer;

public record CustomerInfo(String customerId, String customerName, String email) {
    public CustomerInfo(Customer customer) {
        this(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail());
    }
}
