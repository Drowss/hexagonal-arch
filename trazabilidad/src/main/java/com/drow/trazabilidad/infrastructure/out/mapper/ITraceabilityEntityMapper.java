package com.drow.trazabilidad.infrastructure.out.mapper;

import com.drow.trazabilidad.domain.model.TraceabilityModel;
import com.drow.trazabilidad.infrastructure.out.entity.TraceabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITraceabilityEntityMapper {

    TraceabilityModel toModel(TraceabilityEntity byOrderId);

    TraceabilityEntity toEntity(TraceabilityModel traceabilityModel);
}
