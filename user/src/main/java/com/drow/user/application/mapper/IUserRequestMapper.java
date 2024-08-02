package com.drow.user.application.mapper;

import com.drow.user.application.dto.request.EmployeeRequestDto;
import com.drow.user.application.dto.request.UserLoginRequestDto;
import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.dto.response.UserResponseDto;
import com.drow.user.domain.model.UserLoginModel;
import com.drow.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    UserModel toUserModel(UserRequestDto userRequestDto);

    UserLoginModel toUserLoginModel(UserLoginRequestDto userLoginRequestDto);

    UserModel toEmployeeModel(EmployeeRequestDto employeeRequestDto);

    UserModel toClientModel(EmployeeRequestDto employeeRequestDto);
}
