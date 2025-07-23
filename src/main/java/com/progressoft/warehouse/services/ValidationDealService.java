package com.progressoft.warehouse.services;

import com.progressoft.warehouse.dto.DealRequest;

public interface ValidationDealService {
    void dealValidator(String dealId, String currencyFrom, String currencyTo);
}
