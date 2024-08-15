package com.drow.trazabilidad.domain.usecase;

import com.drow.trazabilidad.domain.api.ITraceabilityServicePort;
import com.drow.trazabilidad.domain.model.TraceabilityModel;
import com.drow.trazabilidad.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    @Override
    public void saveTraceability(TraceabilityModel traceabilityModel) {
        traceabilityPersistencePort.saveTraceability(traceabilityModel);
    }

    @Override
    public TraceabilityModel findTraceabilityByOrderId(Integer orderId) {
        TraceabilityModel traceabilityModel = traceabilityPersistencePort.findTraceabilityByOrderId(orderId);
        System.out.println(traceabilityModel.getId() + "En usecase");
        return traceabilityModel;
    }
}
