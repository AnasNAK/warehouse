package com.progressoft.warehouse.services;

import com.progressoft.warehouse.dto.DealRequest;
import com.progressoft.warehouse.dto.DealResponse;

public interface DealService {

    DealResponse saveDeal(DealRequest dealRequestDto);
}
