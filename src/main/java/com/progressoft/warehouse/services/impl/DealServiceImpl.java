package com.progressoft.warehouse.services.impl;

import com.progressoft.warehouse.dto.DealRequest;
import com.progressoft.warehouse.dto.DealResponse;
import com.progressoft.warehouse.entities.Deal;
import com.progressoft.warehouse.exceptions.DuplicateRequestException;
import com.progressoft.warehouse.mappers.DealMapper;
import com.progressoft.warehouse.repositories.DealRepository;
import com.progressoft.warehouse.services.DealService;
import com.progressoft.warehouse.services.ValidationDealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final ValidationDealService validationDealService;
    private final DealMapper dealMapper;

    @Override
    public DealResponse saveDeal(DealRequest dealRequestDto) {
        log.debug("Starting saveDeal with id={}, fromCurrency={}, toCurrency={}",
                dealRequestDto.getId(), dealRequestDto.getFromCurrency(), dealRequestDto.getToCurrency());

        validationDealService.dealValidator(dealRequestDto.getId(), dealRequestDto.getFromCurrency(), dealRequestDto.getToCurrency());

        Optional<Deal> existingDeal = dealRepository.findById(dealRequestDto.getId());
        if (existingDeal.isPresent()) {
            String errMsg = "Deal with id " + dealRequestDto.getId() + " already exists.";
            log.warn(errMsg);
            throw new DuplicateRequestException(errMsg);
        }

        Deal deal = dealMapper.toEntity(dealRequestDto);

        if (deal.getTimestamp() == null) {
            deal.setTimestamp(Instant.now());
            log.debug("Timestamp not provided, set to current time: {}", deal.getTimestamp());
        }

        Deal savedDeal = dealRepository.save(deal);
        log.info("Deal saved successfully with id={}", savedDeal.getId());

        DealResponse response = dealMapper.toDto(savedDeal);
        log.debug("Returning DealResponse for id={}", response.getId());

        return response;
    }
}
