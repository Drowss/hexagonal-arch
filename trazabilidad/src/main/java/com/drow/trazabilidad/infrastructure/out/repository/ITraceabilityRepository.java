package com.drow.trazabilidad.infrastructure.out.repository;

import com.drow.trazabilidad.infrastructure.out.entity.TraceabilityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITraceabilityRepository extends MongoRepository<TraceabilityEntity, String> {
    TraceabilityEntity findByOrderId(Integer orderId);
}
