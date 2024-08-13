package com.drow.mensajeria.infrastructure.out.mapper;

import com.drow.mensajeria.domain.model.PinUserModel;
import com.drow.mensajeria.infrastructure.out.entity.PinUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPinUserEntityMapper {
    PinUserEntity toEntity(PinUserModel pinUserModel);

    PinUserModel toModel(PinUserEntity byUserIdAndOrderId);
}
