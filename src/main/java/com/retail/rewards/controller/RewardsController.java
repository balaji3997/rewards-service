package com.retail.rewards.controller;


import com.retail.rewards.model.RewardsSummaryResponse;
import com.retail.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    @GetMapping("/{customerId}/rewards")
    public ResponseEntity<RewardsSummaryResponse> getRewardsSummary(@PathVariable(name = "customerId") String customerId,
                                                                    @RequestParam(name = "months", required = false, defaultValue = "3") Integer months,
                                                                    @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return new ResponseEntity<>(rewardsService.customerRewardsSummary(customerId, months, startDate, endDate), HttpStatus.OK);
    }
}
