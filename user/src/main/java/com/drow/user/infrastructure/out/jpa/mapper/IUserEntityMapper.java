package com.drow.user.infrastructure.out.jpa.mapper;

import com.drow.user.domain.model.UserModel;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {
    UserEntity toEntity(UserModel user);
    UserModel toUserModel(UserEntity userEntity);
}
