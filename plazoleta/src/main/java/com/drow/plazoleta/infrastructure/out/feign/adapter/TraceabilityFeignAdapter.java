package com.drow.plazoleta.infrastructure.out.feign.adapter;

import com.drow.plazoleta.domain.dto.RestaurantEfficiencyDto;
import com.drow.plazoleta.domain.dto.TraceabilityResponseDto;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.spi.TraceabilityFeignPort;
import com.drow.plazoleta.infrastructure.out.feign.TraceabilityFeignClientAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements TraceabilityFeignPort {

    private final TraceabilityFeignClientAdapter traceabilityFeignClientAdapter;

    @Override
    public void saveTraceability(TraceabilityResponseDto responseDto) {
        traceabilityFeignClientAdapter.saveTraceability(responseDto);
    }

    @Override
    public TraceabilityResponseDto findTraceabilityByOrderId(Integer orderId) {
        return traceabilityFeignClientAdapter.findTraceabilityByOrderId(orderId);
    }

    @Override
    public void deleteTraceabilityByOrderId(String orderId) {
        traceabilityFeignClientAdapter.deleteTraceabilityByOrderId(orderId);
    }

    @Override
    public RestaurantEfficiencyDto restaurantEfficiency(List<OrderModel> orderModelList) {
        return traceabilityFeignClientAdapter.restaurantEfficiency(orderModelList);
    }
}
