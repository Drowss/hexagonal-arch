package com.drow.user.domain.api;

import com.drow.user.application.dto.response.TokenResponseDto;
import com.drow.user.domain.model.UserLoginModel;
import com.drow.user.domain.model.UserModel;
import jakarta.servlet.http.HttpServletResponse;

public interface IUserServicePort {
    UserModel saveUser(UserModel ownerModel);

    TokenResponseDto loginUser(UserLoginModel userLoginModel, HttpServletResponse response);

    UserModel getUserByCorreo(String correo);
}
