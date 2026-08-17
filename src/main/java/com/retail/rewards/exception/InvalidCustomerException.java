package com.retail.rewards.exception;

public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException(String customerId) {
        super(customerId + " Not found!");
    }

}
