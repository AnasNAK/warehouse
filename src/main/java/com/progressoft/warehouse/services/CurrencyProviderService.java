package com.progressoft.warehouse.services;

import java.util.Currency;
import java.util.Set;

public interface CurrencyProviderService {

    boolean isValidCurrencyCode(String CurrencyCode);

    Set<String> getSupportedCurrencyCodes();

    Currency getCurrency(String currencyCode);
}
