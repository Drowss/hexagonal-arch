package com.drow.user.application.handler;

import com.drow.user.application.dto.request.UserLoginRequestDto;
import com.drow.user.application.dto.request.UserRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface IUserHandler {

    void saveUser(UserRequestDto userRequestDto);

    void loginUser(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response);
}
