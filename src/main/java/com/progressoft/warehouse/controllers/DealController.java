package com.progressoft.warehouse.controllers;

import com.progressoft.warehouse.dto.DealRequest;
import com.progressoft.warehouse.dto.DealResponse;
import com.progressoft.warehouse.services.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping
    public ResponseEntity<DealResponse> createDeal(@Valid @RequestBody DealRequest dealRequest) {
        log.info("Received request to create deal: {}", dealRequest);
        DealResponse dealResponse = dealService.saveDeal(dealRequest);
        log.info("Deal created successfully: {}", dealResponse);
        return new ResponseEntity<>(dealResponse, HttpStatus.CREATED);
    }

}
