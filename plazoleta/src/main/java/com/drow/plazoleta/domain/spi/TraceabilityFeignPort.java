package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.dto.RestaurantEfficiencyDto;
import com.drow.plazoleta.domain.dto.TraceabilityResponseDto;
import com.drow.plazoleta.domain.model.OrderModel;

import java.util.List;

public interface TraceabilityFeignPort {
    void saveTraceability(TraceabilityResponseDto responseDto);

    TraceabilityResponseDto findTraceabilityByOrderId(Integer orderId);

    void deleteTraceabilityByOrderId(String orderId);

    RestaurantEfficiencyDto restaurantEfficiency(List<OrderModel> orderModelList);
}
