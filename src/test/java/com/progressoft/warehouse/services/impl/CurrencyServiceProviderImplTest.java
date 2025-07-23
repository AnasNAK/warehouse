package com.progressoft.warehouse.services.impl;

import com.progressoft.warehouse.services.CurrencyProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceProviderImplTest {

    private CurrencyProviderService currencyProviderService;
    private Set<String> mockAvailableCurrencies;

    @BeforeEach
    void setUp() {
        mockAvailableCurrencies = Set.of("USD", "EUR", "GBP", "JPY", "CAD", "AUD");
        currencyProviderService = new CurrencyServiceProviderImpl(mockAvailableCurrencies);
    }

    @Test
    void shouldReturnTrueForValidCurrencyCode() {
        assertTrue(currencyProviderService.isValidCurrencyCode("USD"));
    }

    @Test
    void shouldReturnTrueForValidLowercaseCurrencyCode() {
        assertTrue(currencyProviderService.isValidCurrencyCode("eur"));
    }

    @Test
    void shouldReturnFalseForInvalidCurrencyCode() {
        assertFalse(currencyProviderService.isValidCurrencyCode("XXX"));
    }

    @Test
    void shouldReturnFalseForNullCurrencyCode() {
        assertFalse(currencyProviderService.isValidCurrencyCode(null));
    }

    @Test
    void shouldReturnAllSupportedCurrencyCodes() {
        Set<String> result = currencyProviderService.getSupportedCurrencyCodes();
        assertThat(result).hasSize(6);
        assertThat(result).containsExactlyInAnyOrder("USD", "EUR", "GBP", "JPY", "CAD", "AUD");
    }

    @Test
    void shouldReturnEmptySetWhenNoCurrenciesAvailable() {
        CurrencyProviderService emptyService = new CurrencyServiceProviderImpl(Set.of());
        Set<String> result = emptyService.getSupportedCurrencyCodes();
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnCurrencyInstanceForValidCode() {
        Currency result = currencyProviderService.getCurrency("USD");
        assertThat(result).isNotNull();
        assertThat(result.getCurrencyCode()).isEqualTo("USD");
    }

    @Test
    void shouldReturnCurrencyInstanceForLowercaseCode() {
        Currency result = currencyProviderService.getCurrency("gbp");
        assertThat(result).isNotNull();
        assertThat(result.getCurrencyCode()).isEqualTo("GBP");
    }

    @Test
    void shouldReturnNullForInvalidCurrencyCode() {
        Currency result = currencyProviderService.getCurrency("INVALID");
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnNullForNullCurrencyCode() {
        Currency result = currencyProviderService.getCurrency(null);
        assertThat(result).isNull();
    }
}