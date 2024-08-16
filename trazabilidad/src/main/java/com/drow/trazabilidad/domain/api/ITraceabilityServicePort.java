package com.drow.trazabilidad.domain.api;

import com.drow.trazabilidad.domain.dto.OrderModelDto;
import com.drow.trazabilidad.domain.dto.response.RestaurantEfficiencyDto;
import com.drow.trazabilidad.domain.model.TraceabilityModel;

import java.util.List;

public interface ITraceabilityServicePort {
    void saveTraceability(TraceabilityModel traceabilityModel);

    TraceabilityModel findTraceabilityByOrderId(Integer orderId);

    void deleteTraceabilityByOrderId(String orderId);

    RestaurantEfficiencyDto getEfficiency(List<OrderModelDto> orderModelDtos);
}
