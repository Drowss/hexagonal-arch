package com.drow.user.application.handler.impl;

import com.drow.user.application.dto.request.UserLoginRequestDto;
import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.exception.UserUnderageException;
import com.drow.user.application.handler.IUserHandler;
import com.drow.user.application.mapper.IUserRequestMapper;
import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.Rol;
import com.drow.user.domain.model.UserLoginModel;
import com.drow.user.domain.model.UserModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        LocalDate fechaNacimiento = userRequestDto.getFechaNacimiento();
        if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) throw new UserUnderageException("El usuario debe ser mayor de edad");
        UserModel userModel = userRequestMapper.toUserModel(userRequestDto);
        userModel.setClave(passwordEncoder.encode(userRequestDto.getClave()));
        userModel.setRol(Rol.PROPIETARIO);
        userServicePort.saveUser(userModel);
    }

    @Override
    public void loginUser(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        UserLoginModel userLoginModel = userRequestMapper.toUserLoginModel(userLoginRequestDto);
        userServicePort.loginUser(userLoginModel, response);
    }
}
