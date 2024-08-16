package com.drow.trazabilidad.application.handler;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.application.dto.response.TraceabilityResponseDto;
import com.drow.trazabilidad.domain.dto.OrderModelDto;
import com.drow.trazabilidad.domain.dto.response.RestaurantEfficiencyDto;

import java.util.List;

public interface ITraceabilityHandler {
    void saveTraceability(TraceabilityRequestDto traceabilityRequestDto);

    TraceabilityResponseDto findTraceabilityByOrderId(Integer orderId);

    void deleteTraceabilityByOrderId(String orderId);

    RestaurantEfficiencyDto getEfficiency(List<OrderModelDto> orderModelDtos);
}
