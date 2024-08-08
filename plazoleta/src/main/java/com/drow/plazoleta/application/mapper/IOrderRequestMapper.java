package com.drow.plazoleta.application.mapper;

import com.drow.plazoleta.application.dto.response.OrderResponseDto;
import com.drow.plazoleta.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    OrderResponseDto toOrderResponseDto(OrderModel orderModel);
}
