package com.progressoft.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class CurrencyConfig {

    @Bean
    public Set<String> availableCurrencies(){
        return Currency.getAvailableCurrencies()
                .stream()
                .map(Currency::getCurrencyCode)
                .collect(Collectors.toSet());
    }
}
