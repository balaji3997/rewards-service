package com.retail.rewards;

import com.retail.rewards.service.RewardsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

    @InjectMocks
    private RewardsService rewardsService;

    @Test
    @DisplayName("Should return 0 reward points for amount 49.00")
    void shouldReturnZeroRewardPointsForAmount49() {
    }

    @Test
    @DisplayName("Should return 0 reward points for amount 50.00")
    void shouldReturnZeroRewardPointsForAmount50() {
        assertEquals(BigDecimal.ZERO, rewardsService.getRewardPoints(new BigDecimal("50.00")));
    }

    @Test
    @DisplayName("Should return 30 reward points for amount 80.00")
    void shouldReturnThirtyRewardPointsForAmount80() {
        assertEquals(BigDecimal.valueOf(30), rewardsService.getRewardPoints(new BigDecimal("80.00")));
    }

    @Test
    @DisplayName("Should return 50 reward points for amount 100.00")
    void shouldReturnFiftyRewardPointsForAmount100() {
        assertEquals(BigDecimal.valueOf(50), rewardsService.getRewardPoints(new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Should return 90 reward points for amount 120.00")
    void shouldReturnNinetyRewardPointsForAmount120() {
        assertEquals(BigDecimal.valueOf(90), rewardsService.getRewardPoints(new BigDecimal("120.00")));
    }

    @Test
    @DisplayName("Should return 150 reward points for amount 150.00")
    void shouldReturnOneHundredFiftyRewardPointsForAmount150() {
        assertEquals(BigDecimal.valueOf(150), rewardsService.getRewardPoints(new BigDecimal("150.00")));
    }

    @Test
    @DisplayName("Should return 250 reward points for amount 200.00")
    void shouldReturnTwoHundredFiftyRewardPointsForAmount200() {
        assertEquals(BigDecimal.valueOf(250), rewardsService.getRewardPoints(new BigDecimal("200.00")));
    }

    @Test
    @DisplayName("Should return 350 reward points for amount 250.00")
    void shouldReturnThreeHundredFiftyRewardPointsForAmount250() {
        assertEquals(BigDecimal.valueOf(350), rewardsService.getRewardPoints(new BigDecimal("250.00")));
    }
}
