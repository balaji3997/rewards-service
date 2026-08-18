package com.retail.rewards;


import com.retail.rewards.controller.RewardsController;
import com.retail.rewards.exception.InvalidCustomerException;
import com.retail.rewards.model.CustomerInfo;
import com.retail.rewards.model.RewardsSummaryResponse;
import com.retail.rewards.service.RewardsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RewardsController.class)
public class RewardsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Test
    @DisplayName("Should return rewards summary for a valid customer ID")
    void shouldReturnRewardsSummaryForValidCustomerId() throws Exception {
        RewardsSummaryResponse mockResponse = new RewardsSummaryResponse(
                new CustomerInfo("CUST001", "Amit", "amit@service.com"),
                LocalDate.of(2026, 5, 27),
                LocalDate.of(2026, 7, 20),
                new BigDecimal("50.00"),
                new BigDecimal("100.00"),
                1,
                Collections.emptyList()
        );
        Mockito.when(rewardsService.customerRewardsSummary(anyString(), anyInt(), any(), any()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/customers/CUST001/rewards")
                        .param("months", "3")
                        .param("startDate", "2026-05-27") // Corrected date format
                        .param("endDate", "2026-07-20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRewardPoints").exists())
                .andExpect(jsonPath("$.totalTransactionAmount").exists())
                .andExpect(jsonPath("$.totalTransactionCount").exists());

        Mockito.verify(rewardsService, Mockito.times(1))
                .customerRewardsSummary(eq("CUST001"), eq(3), eq(LocalDate.of(2026, 5, 27)), eq(LocalDate.of(2026, 7, 20)));
    }

    @Test
    @DisplayName("Should return 400 Bad Request for invalid date format")
    void shouldReturnBadRequestForInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/v1/customers/CUST001/rewards")
                        .param("startDate", "invalid-date")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 404 Not Found for non-existent customer ID")
    void shouldReturnNotFoundForNonExistentCustomerId() throws Exception {

        // FIX: Use any() for all arguments to ensure it matches ("CUST1234", null, null, null)
        Mockito.when(rewardsService.customerRewardsSummary(
                        eq("CUST1234"),
                        any(),
                        any(),
                        any()))
                .thenThrow(new InvalidCustomerException("CUST1234"));

        // Perform request and verify 404
        mockMvc.perform(get("/api/v1/customers/CUST1234/rewards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
