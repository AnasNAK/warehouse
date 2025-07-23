package com.progressoft.warehouse.services.impl;

import com.progressoft.warehouse.services.ValidationDealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationDealServiceImplTest {

    @Mock
    private ValidationDealService validationDealService;

    @Test
    void shouldCallDealValidatorWithValidParameters() {
        validationDealService.dealValidator("DEAL001", "USD", "EUR");

        verify(validationDealService).dealValidator("DEAL001", "USD", "EUR");
    }

    @Test
    void shouldCallDealValidatorWithDifferentCurrencies() {
        validationDealService.dealValidator("DEAL002", "GBP", "JPY");

        verify(validationDealService).dealValidator("DEAL002", "GBP", "JPY");
    }

    @Test
    void shouldCallDealValidatorWithNullDealId() {
        validationDealService.dealValidator(null, "USD", "EUR");

        verify(validationDealService).dealValidator(null, "USD", "EUR");
    }

    @Test
    void shouldCallDealValidatorWithNullCurrencyFrom() {
        validationDealService.dealValidator("DEAL001", null, "EUR");

        verify(validationDealService).dealValidator("DEAL001", null, "EUR");
    }

    @Test
    void shouldCallDealValidatorWithNullCurrencyTo() {
        validationDealService.dealValidator("DEAL001", "USD", null);

        verify(validationDealService).dealValidator("DEAL001", "USD", null);
    }

    @Test
    void shouldCallDealValidatorWithAllNullParameters() {
        validationDealService.dealValidator(null, null, null);

        verify(validationDealService).dealValidator(null, null, null);
    }
}