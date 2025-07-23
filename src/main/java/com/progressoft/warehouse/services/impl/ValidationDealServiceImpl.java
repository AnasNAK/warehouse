package com.progressoft.warehouse.services.impl;

import com.progressoft.warehouse.exceptions.DuplicateRequestException;
import com.progressoft.warehouse.exceptions.ResourceNotFoundException;
import com.progressoft.warehouse.exceptions.RuleViolationException;
import com.progressoft.warehouse.services.ValidationDealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationDealServiceImpl implements ValidationDealService {

    @Override
    public void dealValidator(String dealId, String currencyFrom, String currencyTo) {
        log.debug("Starting deal validation: dealId={}, currencyFrom={}, currencyTo={}", dealId, currencyFrom, currencyTo);

        if (dealId == null || dealId.trim().isEmpty()) {
            String message = "Deal ID must not be null or empty";
            log.warn(message);
            throw new RuleViolationException(message);
        }

        if (currencyFrom == null) {
            String message = "Source currency must not be null";
            log.warn(message);
            throw new RuleViolationException(message);
        }
        if (!isValidCurrency(currencyFrom)) {
            String message = String.format("Invalid source currency code format: %s", currencyFrom);
            log.warn(message);
            throw new RuleViolationException(message);
        }

        if (currencyTo == null) {
            String message = "Target currency must not be null";
            log.warn(message);
            throw new RuleViolationException(message);
        }
        if (!isValidCurrency(currencyTo)) {
            String message = String.format("Invalid target currency code format: %s", currencyTo);
            log.warn(message);
            throw new RuleViolationException(message);
        }

        if (currencyFrom.equalsIgnoreCase(currencyTo)) {
            String message = "CurrencyFrom and CurrencyTo cannot be the same";
            log.warn(message);
            throw new RuleViolationException(message);
        }

        log.info("Validation completed successfully for dealId={}", dealId);
    }

    private boolean isValidCurrency(String currency) {
        return currency.matches("^[A-Z]{3}$");
    }
}
