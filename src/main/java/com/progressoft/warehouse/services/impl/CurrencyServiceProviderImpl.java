package com.progressoft.warehouse.services.impl;

import com.progressoft.warehouse.services.CurrencyProviderService;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceProviderImpl implements CurrencyProviderService {

    private final Set<String> availableCurrencies;

    public CurrencyServiceProviderImpl(Set<String> availableCurrencies){
        this.availableCurrencies = availableCurrencies;

    }


    @Override
    public boolean isValidCurrencyCode(String CurrencyCode){

        if (CurrencyCode == null) return false;

        return availableCurrencies.contains(CurrencyCode.toUpperCase());
    }

    @Override
    public Set<String> getSupportedCurrencyCodes() {
        return availableCurrencies;
    }

    @Override
    public Currency getCurrency(String CurrencyCode){
        if (!isValidCurrencyCode(CurrencyCode)) return null;
        return Currency.getInstance(CurrencyCode.toUpperCase());
    }

}
