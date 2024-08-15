package com.drow.user.application.handler.impl;

import com.drow.user.application.dto.request.EmployeeRequestDto;
import com.drow.user.application.dto.request.UserLoginRequestDto;
import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.dto.response.TokenResponseDto;
import com.drow.user.application.dto.response.UserResponseDto;
import com.drow.user.application.exception.UserUnderageException;
import com.drow.user.application.feign.PlazoletaFeignClient;
import com.drow.user.application.feign.dto.RestaurantEmployeeRequestDto;
import com.drow.user.application.handler.IUserHandler;
import com.drow.user.application.mapper.IUserRequestMapper;
import com.drow.user.application.mapper.IUserResponseMapper;
import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.Rol;
import com.drow.user.domain.model.UserLoginModel;
import com.drow.user.domain.model.UserModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;
    private final PlazoletaFeignClient plazoletaFeignClient;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toUserModel(userRequestDto);
        LocalDate fechaNacimiento = userModel.getFechaNacimiento();
        if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) throw new UserUnderageException("El usuario debe ser mayor de edad");
        userModel.setRol(Rol.PROPIETARIO);
        userServicePort.saveUser(userModel);
    }

    @Override
    public TokenResponseDto loginUser(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        UserLoginModel userLoginModel = userRequestMapper.toUserLoginModel(userLoginRequestDto);
        return userServicePort.loginUser(userLoginModel, response);
    }

    @Override
    public UserResponseDto getUserByCorreo(String correo) {
        return userResponseMapper.toUserResponseDto(userServicePort.getUserByCorreo(correo));
    }

    @Override
    public void saveEmployee(EmployeeRequestDto employeeRequestDto, String token) {
        UserModel userModel = userRequestMapper.toEmployeeModel(employeeRequestDto);
        userModel.setRol(Rol.EMPLEADO);
        UserModel user = userServicePort.saveUser(userModel);
        RestaurantEmployeeRequestDto restaurantEmployeeRequestDto = new RestaurantEmployeeRequestDto();
        restaurantEmployeeRequestDto.setEmployeeId(user.getDocumentoDeIdentidad());
        plazoletaFeignClient.saveRestaurantEmployee(restaurantEmployeeRequestDto);
    }

    @Override
    public void saveClient(EmployeeRequestDto employeeRequestDto) {
        UserModel userModel = userRequestMapper.toClientModel(employeeRequestDto);
        userModel.setRol(Rol.CLIENTE);
        userServicePort.saveUser(userModel);
    }
}
