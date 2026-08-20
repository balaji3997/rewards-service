package com.retail.rewards.model;

import java.math.BigDecimal;
import java.time.YearMonth;

public record MonthlyRewardInfo(YearMonth month, BigDecimal rewardPoints, Integer monthlyTransactionsCount,
                                BigDecimal monthlyTransactionAmount) {

}
