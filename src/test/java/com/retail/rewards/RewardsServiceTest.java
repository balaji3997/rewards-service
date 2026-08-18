package com.retail.rewards;

import com.retail.rewards.service.RewardsService;
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
    void shouldCalculateRewardPointsCorrectly() {
        assertEquals(BigDecimal.ZERO,
                rewardsService.getRewardPoints(new BigDecimal("49.00")));

        assertEquals(BigDecimal.ZERO,
                rewardsService.getRewardPoints(new BigDecimal("50.00")));

        assertEquals(BigDecimal.valueOf(30),
                rewardsService.getRewardPoints(new BigDecimal("80.00")));

        assertEquals(BigDecimal.valueOf(50),
                rewardsService.getRewardPoints(new BigDecimal("100.00")));

        assertEquals(BigDecimal.valueOf(90),
                rewardsService.getRewardPoints(new BigDecimal("120.00")));

        assertEquals(BigDecimal.valueOf(150),
                rewardsService.getRewardPoints(new BigDecimal("150.00")));

        assertEquals(BigDecimal.valueOf(250),
                rewardsService.getRewardPoints(new BigDecimal("200.00")));

        assertEquals(BigDecimal.valueOf(350),
                rewardsService.getRewardPoints(new BigDecimal("250.00")));
    }
}
