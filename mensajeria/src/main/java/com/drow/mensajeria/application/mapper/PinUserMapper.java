package com.drow.mensajeria.application.mapper;

import com.drow.mensajeria.application.dto.response.PinUserResponseDto;
import com.drow.mensajeria.domain.model.PinUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.http.ResponseEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PinUserMapper {
    PinUserResponseDto toResponse(PinUserModel pinUser);
}
