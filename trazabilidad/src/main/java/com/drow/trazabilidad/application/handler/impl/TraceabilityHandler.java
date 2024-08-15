package com.drow.trazabilidad.application.handler.impl;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.application.dto.response.TraceabilityResponseDto;
import com.drow.trazabilidad.application.handler.ITraceabilityHandler;
import com.drow.trazabilidad.application.mapper.TraceabilityMapper;
import com.drow.trazabilidad.domain.api.ITraceabilityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
