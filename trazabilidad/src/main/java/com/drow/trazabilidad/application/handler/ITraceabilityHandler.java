package com.drow.trazabilidad.application.handler;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.application.dto.response.TraceabilityResponseDto;

public interface ITraceabilityHandler {
    void saveTraceability(TraceabilityRequestDto traceabilityRequestDto);

    TraceabilityResponseDto findTraceabilityByOrderId(Integer orderId);
}
