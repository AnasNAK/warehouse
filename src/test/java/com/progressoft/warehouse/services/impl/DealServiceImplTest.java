package com.progressoft.warehouse.services.impl;

import com.progressoft.warehouse.dto.DealRequest;
import com.progressoft.warehouse.dto.DealResponse;
import com.progressoft.warehouse.entities.Deal;
import com.progressoft.warehouse.exceptions.DuplicateRequestException;
import com.progressoft.warehouse.mappers.DealMapper;
import com.progressoft.warehouse.repositories.DealRepository;
import com.progressoft.warehouse.services.ValidationDealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private ValidationDealService validationDealService;

    @Mock
    private DealMapper dealMapper;

    @InjectMocks
    private DealServiceImpl dealService;

    private DealRequest dealRequest;
    private Deal deal;
    private DealResponse dealResponse;

    @BeforeEach
    void setUp() {
        dealRequest = new DealRequest();
        dealRequest.setId("DEAL001");
        dealRequest.setFromCurrency("USD");
        dealRequest.setToCurrency("EUR");
        dealRequest.setAmount(BigDecimal.valueOf(1000));

        deal = new Deal();
        deal.setId("DEAL001");
        deal.setFromCurrency("USD");
        deal.setToCurrency("EUR");
        deal.setAmount(BigDecimal.valueOf(1000));
        deal.setTimestamp(Instant.now());

        dealResponse = new DealResponse();
        dealResponse.setId("DEAL001");
        dealResponse.setFromCurrency("USD");
        dealResponse.setToCurrency("EUR");
        dealResponse.setAmount(BigDecimal.valueOf(1000));
    }

    @Test
    void shouldSaveDealSuccessfully() {
        when(dealRepository.findById("DEAL001")).thenReturn(Optional.empty());
        when(dealMapper.toEntity(dealRequest)).thenReturn(deal);
        when(dealRepository.save(any(Deal.class))).thenReturn(deal);
        when(dealMapper.toDto(deal)).thenReturn(dealResponse);

        DealResponse result = dealService.saveDeal(dealRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("DEAL001");
        verify(validationDealService).dealValidator("DEAL001", "USD", "EUR");
        verify(dealRepository).save(any(Deal.class));
    }

    @Test
    void shouldSaveDealWithTimestampWhenNotProvided() {
        Deal dealWithoutTimestamp = new Deal();
        dealWithoutTimestamp.setId("DEAL001");
        dealWithoutTimestamp.setFromCurrency("USD");
        dealWithoutTimestamp.setToCurrency("EUR");
        dealWithoutTimestamp.setAmount(BigDecimal.valueOf(1000));

        when(dealRepository.findById("DEAL001")).thenReturn(Optional.empty());
        when(dealMapper.toEntity(dealRequest)).thenReturn(dealWithoutTimestamp);
        when(dealRepository.save(any(Deal.class))).thenReturn(deal);
        when(dealMapper.toDto(deal)).thenReturn(dealResponse);

        DealResponse result = dealService.saveDeal(dealRequest);

        assertThat(result).isNotNull();
        verify(dealRepository).save(argThat(savedDeal -> savedDeal.getTimestamp() != null));
    }

    @Test
    void shouldPreserveTimestampWhenProvided() {
        Instant providedTimestamp = Instant.parse("2023-01-01T00:00:00Z");
        Deal dealWithTimestamp = new Deal();
        dealWithTimestamp.setId("DEAL001");
        dealWithTimestamp.setFromCurrency("USD");
        dealWithTimestamp.setToCurrency("EUR");
        dealWithTimestamp.setAmount(BigDecimal.valueOf(1000));
        dealWithTimestamp.setTimestamp(providedTimestamp);

        when(dealRepository.findById("DEAL001")).thenReturn(Optional.empty());
        when(dealMapper.toEntity(dealRequest)).thenReturn(dealWithTimestamp);
        when(dealRepository.save(any(Deal.class))).thenReturn(dealWithTimestamp);
        when(dealMapper.toDto(dealWithTimestamp)).thenReturn(dealResponse);

        DealResponse result = dealService.saveDeal(dealRequest);

        assertThat(result).isNotNull();
        verify(dealRepository).save(argThat(savedDeal -> savedDeal.getTimestamp().equals(providedTimestamp)));
    }

    @Test
    void shouldThrowDuplicateRequestExceptionWhenDealExists() {
        when(dealRepository.findById("DEAL001")).thenReturn(Optional.of(deal));

        DuplicateRequestException exception = assertThrows(DuplicateRequestException.class,
                () -> dealService.saveDeal(dealRequest));

        assertThat(exception.getMessage()).contains("Deal with id DEAL001 already exists");
        verify(dealRepository, never()).save(any(Deal.class));
    }

    @Test
    void shouldThrowDuplicateRequestExceptionForExistingDealWithDifferentData() {
        Deal existingDeal = new Deal();
        existingDeal.setId("DEAL001");
        existingDeal.setFromCurrency("GBP");
        existingDeal.setToCurrency("JPY");
        existingDeal.setAmount(BigDecimal.valueOf(2000));

        when(dealRepository.findById("DEAL001")).thenReturn(Optional.of(existingDeal));

        DuplicateRequestException exception = assertThrows(DuplicateRequestException.class,
                () -> dealService.saveDeal(dealRequest));

        assertThat(exception.getMessage()).contains("Deal with id DEAL001 already exists");
        verify(validationDealService).dealValidator("DEAL001", "USD", "EUR");
        verify(dealRepository, never()).save(any(Deal.class));
    }

    @Test
    void shouldCallValidationServiceBeforeProcessing() {
        when(dealRepository.findById("DEAL001")).thenReturn(Optional.empty());
        when(dealMapper.toEntity(dealRequest)).thenReturn(deal);
        when(dealRepository.save(any(Deal.class))).thenReturn(deal);
        when(dealMapper.toDto(deal)).thenReturn(dealResponse);

        dealService.saveDeal(dealRequest);

        verify(validationDealService).dealValidator("DEAL001", "USD", "EUR");
    }
}