package com.drow.user.application.mapper;

import com.drow.user.application.dto.response.UserResponseDto;
import com.drow.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
    UserResponseDto toUserResponseDto(UserModel userModel);
}
