package com.drow.trazabilidad.infrastructure.adapter;

import com.drow.trazabilidad.domain.model.TraceabilityModel;
import com.drow.trazabilidad.domain.spi.ITraceabilityPersistencePort;
import com.drow.trazabilidad.infrastructure.out.entity.TraceabilityEntity;
import com.drow.trazabilidad.infrastructure.out.mapper.ITraceabilityEntityMapper;
import com.drow.trazabilidad.infrastructure.out.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityJpaAdapter implements ITraceabilityPersistencePort {

    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityEntityMapper traceabilityEntityMapper;

    @Override
    public void saveTraceability(TraceabilityModel traceabilityModel) {
        TraceabilityEntity traceabilityEntity = traceabilityEntityMapper.toEntity(traceabilityModel);
        traceabilityRepository.save(traceabilityEntity);
    }

    @Override
    public TraceabilityModel findTraceabilityByOrderId(Integer orderId) {
        TraceabilityEntity traceabilityEntity = traceabilityRepository.findByOrderId(orderId);
        return traceabilityEntityMapper.toModel(traceabilityEntity);
    }

    @Override
    public void deleteTraceabilityByOrderId(String orderId) {
        traceabilityRepository.deleteById(orderId);
    }
}
