package com.drow.trazabilidad.application.mapper;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.application.dto.response.TraceabilityResponseDto;
import com.drow.trazabilidad.domain.model.TraceabilityModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface TraceabilityMapper {
    TraceabilityModel toModel(TraceabilityRequestDto traceabilityRequestDto);

    TraceabilityResponseDto toDto(TraceabilityModel traceabilityByOrderId);
}
