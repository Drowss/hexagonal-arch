package com.drow.user.application.handler;

import com.drow.user.application.dto.request.EmployeeRequestDto;
import com.drow.user.application.dto.request.UserLoginRequestDto;
import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.dto.response.TokenResponseDto;
import com.drow.user.application.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;

public interface IUserHandler {

    void saveUser(UserRequestDto userRequestDto);

    TokenResponseDto loginUser(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response);

    UserResponseDto getUserByCorreo(String correo);

    void saveEmployee(EmployeeRequestDto employeeRequestDto, String token);

    void saveClient(EmployeeRequestDto employeeRequestDto);
}
