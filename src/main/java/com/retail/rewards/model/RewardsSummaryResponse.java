package com.retail.rewards.model;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

public class RewardsSummaryResponse {

    private CustomerInfo customerInfo;
    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal totalRewardPoints;

    private BigDecimal totalTransactionAmount;

    private Integer totalTransactionCount;

    private List<MonthlyRewardInfo> monthlyRewards;

    public RewardsSummaryResponse(CustomerInfo customerInfo, LocalDate startDate, LocalDate endDate, BigDecimal totalRewardPoints, BigDecimal totalTransactionAmount, Integer totalTransactionCount, List<MonthlyRewardInfo> monthlyRewards) {
        this.customerInfo = customerInfo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalRewardPoints = totalRewardPoints;
        this.totalTransactionAmount = totalTransactionAmount;
        this.totalTransactionCount = totalTransactionCount;
        this.monthlyRewards = monthlyRewards;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalRewardPoints() {
        return totalRewardPoints;
    }

    public void setTotalRewardPoints(BigDecimal totalRewardPoints) {
        this.totalRewardPoints = totalRewardPoints;
    }

    public BigDecimal getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    public void setTotalTransactionAmount(BigDecimal totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }

    public Integer getTotalTransactionCount() {
        return totalTransactionCount;
    }

    public void setTotalTransactionCount(Integer totalTransactionCount) {
        this.totalTransactionCount = totalTransactionCount;
    }

    public List<MonthlyRewardInfo> getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(List<MonthlyRewardInfo> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }
}
