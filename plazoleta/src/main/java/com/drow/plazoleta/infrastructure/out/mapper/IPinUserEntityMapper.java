package com.drow.plazoleta.infrastructure.out.mapper;

import com.drow.plazoleta.domain.model.PinUserModel;
import com.drow.plazoleta.infrastructure.out.entity.PinUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPinUserEntityMapper {
    PinUserEntity toEntity(PinUserModel pinUserModel);

    PinUserModel toModel(PinUserEntity byUserIdAndOrderId);
}
