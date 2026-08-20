package com.retail.rewards.model;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

public record RewardsSummaryResponse(
        CustomerInfo customerInfo,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal totalRewardPoints,
        BigDecimal totalTransactionAmount,
        int totalTransactionCount,
        List<MonthlyRewardInfo> monthlyRewards
) {
}
