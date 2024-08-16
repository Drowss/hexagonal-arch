package com.drow.trazabilidad.domain.spi;

import com.drow.trazabilidad.domain.model.TraceabilityModel;

public interface ITraceabilityPersistencePort {
    void saveTraceability(TraceabilityModel traceabilityModel);

    TraceabilityModel findTraceabilityByOrderId(Integer orderId);

    void deleteTraceabilityByOrderId(String orderId);
}
