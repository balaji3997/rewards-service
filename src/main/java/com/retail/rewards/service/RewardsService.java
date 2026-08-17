package com.retail.rewards.service;

import com.retail.rewards.entity.Customer;
import com.retail.rewards.entity.Transaction;
import com.retail.rewards.exception.InvalidCustomerException;
import com.retail.rewards.exception.InvalidDateRangeException;
import com.retail.rewards.model.CustomerInfo;
import com.retail.rewards.model.DateRange;
import com.retail.rewards.model.MonthlyRewardInfo;
import com.retail.rewards.model.RewardsSummaryResponse;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public RewardsSummaryResponse customerRewardsSummary(String customerId, Integer months, LocalDate startDate, LocalDate endDate) {
        DateRange range = resolveDateRange(months, startDate, endDate);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new InvalidCustomerException(customerId));
        List<Transaction> transactions =  transactionRepository.findTransactionsForCustomerInDateRange(customerId, range.getStartDate(), range.getEndDate());
        if(transactions.isEmpty()) {
            throw new InvalidDateRangeException("No valid transactions for the duration specified for Customer Id: " + customerId);
        }
        Map<YearMonth, List<Transaction>> monthwiseTransactions = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> YearMonth.from(transaction.getTransactionDate())));
        List<MonthlyRewardInfo> monthlyRewards = generateMonthlyRewards(monthwiseTransactions, range);
        return buildRewardsSummaryResponse(customer, range.getStartDate().toLocalDate(), range.getEndDate().toLocalDate(), monthlyRewards);
    }

    private DateRange resolveDateRange(Integer months, LocalDate startDate, LocalDate endDate) {
        if (startDate != null || endDate != null) {
            return verifyAndCalculateDateRangeForDuration(startDate, endDate);
        }
        return verifyAndCalculateDateRangeForPastMonths(months);
    }

    private DateRange verifyAndCalculateDateRangeForDuration(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidDateRangeException("Need to provide both startDate and endDate.");
        } else if (endDate.isBefore(startDate)) {
            throw new InvalidDateRangeException("Start date must be before end date.");
        }
        return new DateRange(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay().minusSeconds(1));
    }
    private DateRange verifyAndCalculateDateRangeForPastMonths(int months) {
        if (months < 1 || months > 12) {
            throw new InvalidDateRangeException("Month provided is invalid.");
        }
        LocalDateTime currTime = LocalDateTime.now();
        LocalDateTime startTime = currTime.minusMonths(months);
        return new DateRange(startTime, currTime);
    }

    private List<MonthlyRewardInfo> generateMonthlyRewards(Map<YearMonth, List<Transaction>> monthwiseTransactions, DateRange range) {
        List<MonthlyRewardInfo> monthlyRewards = new ArrayList<>();
        for(LocalDateTime month = range.getStartDate(); !month.isAfter(range.getEndDate()); month = month.plusMonths(1)) {
            YearMonth monthInfo = YearMonth.from(month);
            List<Transaction> transactions = monthwiseTransactions.getOrDefault(YearMonth.from(month), Collections.emptyList());
            BigDecimal rewardPoints = transactions.stream().map(transaction -> getRewardPoints(transaction.getAmount())).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal monthlyTransactionAmount = transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            monthlyRewards.add(new MonthlyRewardInfo(monthInfo, rewardPoints, transactions.size(), monthlyTransactionAmount));
        }
        return monthlyRewards;
    }

    private BigDecimal getRewardPoints(BigDecimal amount) {
        int wholeAmount = amount.setScale(0, RoundingMode.FLOOR).intValue();
        if(wholeAmount > 100) {
            return BigDecimal.valueOf((wholeAmount - 100) * 2L + 50);
        } else if(wholeAmount > 50) {
            return BigDecimal.valueOf(wholeAmount - 50);
        }
        return BigDecimal.ZERO;
    }
    private RewardsSummaryResponse buildRewardsSummaryResponse(Customer customer, LocalDate startDate, LocalDate endDate, List<MonthlyRewardInfo> monthlyRewards) {
        BigDecimal totalRewardPoints = monthlyRewards.stream().map(MonthlyRewardInfo::getRewardPoints).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalTransactionAmount = monthlyRewards.stream().map(MonthlyRewardInfo::getMonthlyTransactionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer totalTransactionCount = monthlyRewards.stream().mapToInt(MonthlyRewardInfo::getMonthlyTransactionsCount).sum();
        return new RewardsSummaryResponse(new CustomerInfo(customer), startDate, endDate, totalRewardPoints, totalTransactionAmount,totalTransactionCount, monthlyRewards);
    }
}
