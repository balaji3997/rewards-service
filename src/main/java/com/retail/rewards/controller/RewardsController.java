package com.retail.rewards.controller;


import com.retail.rewards.model.RewardsSummaryResponse;
import com.retail.rewards.service.RewardsService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Validated
@RequestMapping("/api/v1/customers")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/{customerId}/rewards")
    public ResponseEntity<RewardsSummaryResponse> getRewardsSummary(@PathVariable(name = "customerId") @NotBlank(message = "Customer ID cannot be blank") String customerId,
                                                                    @RequestParam(name = "months", required = false) @Min(value = 1, message = "Months must be at least 1") Integer months,
                                                                    @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return new ResponseEntity<>(rewardsService.customerRewardsSummary(customerId, months, startDate, endDate), HttpStatus.OK);
    }
}
