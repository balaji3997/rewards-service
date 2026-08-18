package com.retail.rewards;

import com.retail.rewards.entity.Customer;
import com.retail.rewards.entity.Transaction;
import com.retail.rewards.exception.InvalidCustomerException;
import com.retail.rewards.exception.InvalidDateRangeException;
import com.retail.rewards.model.RewardsSummaryResponse;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.repository.TransactionRepository;
import com.retail.rewards.service.RewardsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

    @InjectMocks
    private RewardsService rewardsService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;


    @Test
    @DisplayName("Should return 0.00 reward points for amount 50.00")
    void shouldReturnZeroRewardPointsForAmount50() {
        assertEquals(BigDecimal.ZERO, rewardsService.getRewardPoints(new BigDecimal("50.00")));
    }

    @Test
    @DisplayName("Should return 29.99 reward points for amount 79.99")
    void shouldReturnTwentyNinePointNineNineRewardPointsForAmount79_99() {
        assertEquals(new BigDecimal("29.99"), rewardsService.getRewardPoints(new BigDecimal("79.99")));
    }

    @Test
    @DisplayName("Should return 50.00 reward points for amount 100.00")
    void shouldReturnFiftyRewardPointsForAmount100() {
        assertEquals(new BigDecimal("50.00"), rewardsService.getRewardPoints(new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Should return 90.02 reward points for amount 120.01")
    void shouldReturnNinetyPointZeroTwoRewardPointsForAmount120_01() {
        assertEquals(new BigDecimal("90.02"), rewardsService.getRewardPoints(new BigDecimal("120.01")));
    }

    @Test
    @DisplayName("Should return 150.50 reward points for amount 150.25")
    void shouldReturnOneHundredFiftyPointFiveRewardPointsForAmount150_25() {
        assertEquals(new BigDecimal("150.50"), rewardsService.getRewardPoints(new BigDecimal("150.25")));
    }

    @Test
    @DisplayName("Should return 250.74 reward points for amount 200.37")
    void shouldReturnTwoHundredFiftyPointSevenFiveRewardPointsForAmount200_37() {
        assertEquals(new BigDecimal("250.74"), rewardsService.getRewardPoints(new BigDecimal("200.37")));
    }

    @Test
    @DisplayName("Should return 350.98 reward points for amount 250.49")
    void shouldReturnThreeHundredFiftyPointNineNineRewardPointsForAmount250_49() {
        assertEquals(new BigDecimal("350.98"), rewardsService.getRewardPoints(new BigDecimal("250.49")));
    }
    @Test
    @DisplayName("Verification of reward points for multiple transactions")
    void shouldCalculateRewardsSummaryForMultipleTransactions() {
        String customerId = "CUST00123";
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 11, 30);

        Customer customer = new Customer(customerId, "Amit", "amit@services.com");
        List<Transaction> transactions = List.of(
                new Transaction("T1", customer, LocalDateTime.of(2023, 10, 5, 10, 0), new BigDecimal("120.00"), "Purchase 1"),
                new Transaction("T2", customer, LocalDateTime.of(2023, 10, 15, 15, 0), new BigDecimal("80.00"), "Purchase 2"),
                new Transaction("T3", customer, LocalDateTime.of(2023, 11, 10, 12, 0), new BigDecimal("200.00"), "Purchase 3"),
                new Transaction("T4", customer, LocalDateTime.of(2023, 11, 20, 18, 0), new BigDecimal("50.00"), "Purchase 4")
        );

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(transactionRepository.findTransactionsForCustomerInDateRange(customerId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59)))
                .thenReturn(transactions);

        RewardsSummaryResponse response = rewardsService.customerRewardsSummary(customerId, 3, startDate, endDate);

        assertEquals(new BigDecimal("120.00"), response.getMonthlyRewards().get(0).getRewardPoints());
        assertEquals(new BigDecimal("250.00"), response.getMonthlyRewards().get(1).getRewardPoints());
        assertEquals(4, response.getTotalTransactionCount());
        assertEquals(new BigDecimal("450.00"), response.getTotalTransactionAmount());

        verify(customerRepository, times(1)).findById(customerId);
        verify(transactionRepository, times(1)).findTransactionsForCustomerInDateRange(customerId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }

    @Test
    @DisplayName("Should throw InvalidCustomerException for non-existent customer ID")
    void shouldThrowInvalidCustomerExceptionForNonExistentCustomerId() {
        when(customerRepository.findById("CUST989")).thenReturn(Optional.empty());

        InvalidCustomerException exception = assertThrows(InvalidCustomerException.class, () ->
                rewardsService.customerRewardsSummary("CUST989", 3, null, null));

        assertEquals("CUST989 Not found!", exception.getMessage());
        verify(customerRepository, times(1)).findById("CUST989");
        verifyNoInteractions(transactionRepository);
    }

    @Test
    @DisplayName("Should throw InvalidDateRangeException for invalid date range")
    void shouldThrowInvalidDateRangeExceptionForInvalidDateRange() {
        LocalDate startDate = LocalDate.of(2023, 12, 31);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        InvalidDateRangeException exception = assertThrows(InvalidDateRangeException.class, () ->
                rewardsService.customerRewardsSummary("CUST001", 3, startDate, endDate));

        assertEquals("Start date must be before end date.", exception.getMessage());
        verifyNoInteractions(customerRepository, transactionRepository);
    }

    @Test
    @DisplayName("Should return empty rewards summary for customer with no transactions")
    void shouldReturnEmptyRewardsSummaryForCustomerWithNoTransactions() {
        Customer mockCustomer = new Customer("CUST001", "John Doe", "john.doe@example.com");
        when(customerRepository.findById("CUST001")).thenReturn(Optional.of(mockCustomer));
        when(transactionRepository.findTransactionsForCustomerInDateRange(anyString(), any(), any()))
                .thenReturn(Collections.emptyList());

        InvalidDateRangeException exc = assertThrows(InvalidDateRangeException.class, () -> rewardsService.customerRewardsSummary("CUST001", 3, null, null));

        assertEquals("No valid transactions for the duration specified for Customer Id: CUST001", exc.getMessage());


        verify(customerRepository, times(1)).findById("CUST001");
        verify(transactionRepository, times(1)).findTransactionsForCustomerInDateRange(anyString(), any(), any());
    }
}
