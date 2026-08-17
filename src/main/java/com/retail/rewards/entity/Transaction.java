package com.retail.rewards.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable =false)
    private Customer customer;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;


    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "purchase_desc")
    private String purchaseDescription;

    protected Transaction() {

    }

    public Transaction(String transactionId, Customer customer, LocalDateTime transactionDate, BigDecimal amount, String purchaseDescription) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.purchaseDescription = purchaseDescription;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPurchaseDescription(String purchaseDescription) {
        this.purchaseDescription = purchaseDescription;
    }
}
