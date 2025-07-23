package com.progressoft.warehouse.mappers;

import com.progressoft.warehouse.dto.DealRequest;
import com.progressoft.warehouse.dto.DealResponse;
import com.progressoft.warehouse.entities.Deal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealMapper {

    Deal toEntity(DealRequest dto);

    DealResponse toDto(Deal deal);
}
