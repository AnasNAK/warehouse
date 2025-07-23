package com.progressoft.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealResponse {

    private String id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private Instant timestamp;
}
