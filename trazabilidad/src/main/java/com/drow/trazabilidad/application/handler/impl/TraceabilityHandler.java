package com.drow.trazabilidad.application.handler.impl;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.application.dto.response.TraceabilityResponseDto;
import com.drow.trazabilidad.application.handler.ITraceabilityHandler;
import com.drow.trazabilidad.application.mapper.TraceabilityMapper;
import com.drow.trazabilidad.domain.api.ITraceabilityServicePort;
import com.drow.trazabilidad.domain.dto.OrderModelDto;
import com.drow.trazabilidad.domain.dto.response.RestaurantEfficiencyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final TraceabilityMapper traceabilityMapper;

    @Override
    public void saveTraceability(TraceabilityRequestDto traceabilityRequestDto) {
        traceabilityServicePort.saveTraceability(traceabilityMapper.toModel(traceabilityRequestDto));
    }

    @Override
    public TraceabilityResponseDto findTraceabilityByOrderId(Integer orderId) {
        return traceabilityMapper.toDto(traceabilityServicePort.findTraceabilityByOrderId(orderId));
    }

    @Override
    public void deleteTraceabilityByOrderId(String orderId) {
        traceabilityServicePort.deleteTraceabilityByOrderId(orderId);
    }

    @Override
    public RestaurantEfficiencyDto getEfficiency(List<OrderModelDto> orderModelDtos) {
        return traceabilityServicePort.getEfficiency(orderModelDtos);
    }
}
