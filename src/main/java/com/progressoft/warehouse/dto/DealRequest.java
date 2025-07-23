package com.progressoft.warehouse.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealRequest {

    @NotBlank(message = "Deal ID is mandatory")
    private String id;

    @NotBlank(message = "From currency is mandatory")
    @Pattern(regexp = "^[A-Z]{3}$", message = "From currency must be a 3-letter ISO code")
    private String fromCurrency;

    @NotBlank(message = "To currency is mandatory")
    @Pattern(regexp = "^[A-Z]{3}$", message = "To currency must be a 3-letter ISO code")
    private String toCurrency;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

}

