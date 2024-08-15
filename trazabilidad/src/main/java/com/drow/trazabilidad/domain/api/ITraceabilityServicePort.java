package com.drow.trazabilidad.domain.api;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.domain.model.TraceabilityModel;

public interface ITraceabilityServicePort {
    void saveTraceability(TraceabilityModel traceabilityModel);

    TraceabilityModel findTraceabilityByOrderId(Integer orderId);
}
