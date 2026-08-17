package com.retail.rewards.model;

import java.math.BigDecimal;
import java.time.YearMonth;

public class MonthlyRewardInfo {
    private YearMonth month;
    private BigDecimal rewardPoints;
    private Integer monthlyTransactionsCount;
    private BigDecimal monthlyTransactionAmount;

    public MonthlyRewardInfo(YearMonth month, BigDecimal rewardPoints, Integer monthlyTransactionsCount, BigDecimal monthlyTransactionAmount) {
        this.month = month;
        this.rewardPoints = rewardPoints;
        this.monthlyTransactionsCount = monthlyTransactionsCount;
        this.monthlyTransactionAmount = monthlyTransactionAmount;
    }

    public YearMonth getMonth() {
        return month;
    }

    public BigDecimal getRewardPoints() {
        return rewardPoints;
    }

    public Integer getMonthlyTransactionsCount() {
        return monthlyTransactionsCount;
    }

    public BigDecimal getMonthlyTransactionAmount() {
        return monthlyTransactionAmount;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public void setRewardPoints(BigDecimal rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public void setMonthlyTransactionsCount(Integer monthlyTransactionsCount) {
        this.monthlyTransactionsCount = monthlyTransactionsCount;
    }

    public void setMonthlyTransactionAmount(BigDecimal monthlyTransactionAmount) {
        this.monthlyTransactionAmount = monthlyTransactionAmount;
    }

    @Override
    public String toString() {
        return "MonthlyRewardInfo{" +
                "month=" + month +
                ", rewardPoints=" + rewardPoints +
                ", monthlyTransactionsCount=" + monthlyTransactionsCount +
                ", monthlyTransactionAmount=" + monthlyTransactionAmount +
                '}';
    }
}
