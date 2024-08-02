package com.drow.user.domain.usecase;

import com.drow.user.application.dto.response.TokenResponseDto;
import com.drow.user.application.exception.IncorrectPassword;
import com.drow.user.application.exception.UserUnderageException;
import com.drow.user.application.jwt.IJwtHandler;
import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.Rol;
import com.drow.user.domain.model.UserLoginModel;
import com.drow.user.domain.model.UserModel;
import com.drow.user.domain.spi.IUserPasswordEncrypter;
import com.drow.user.domain.spi.IUserPersistencePort;
import com.drow.user.infrastructure.exception.UserAlreadyExists;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import com.drow.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

@AllArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IUserPasswordEncrypter userPasswordEncrypter;
    private final IJwtHandler jwtHandler;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void saveUser(UserModel userModel) {
        if (Boolean.TRUE.equals(userPersistencePort.userExists(userModel.getDocumentoDeIdentidad())))
            throw new UserAlreadyExists("El usuario ya existe");
        userModel.setClave(userPasswordEncrypter.encryptPassword(userModel.getClave()));
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public TokenResponseDto loginUser(UserLoginModel userLoginModel, HttpServletResponse response) {
        UserEntity userEntity = userPersistencePort.getUserByCorreo(userLoginModel.getCorreo());
        if (!userPasswordEncrypter.checkPassword(userLoginModel.getClave(), userEntity.getClave()))
            throw new IncorrectPassword("La contraseña es incorrecta");
        Map<String, Object> extraClaims = Map.of("cedula", userEntity.getDocumentoDeIdentidad());
        String token = jwtHandler.generateToken(extraClaims, userEntity.getCorreo());
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(false);
        cookie.setMaxAge((int) Duration.ofDays(1).toSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return new TokenResponseDto(token);
    }

    @Override
    public UserModel getUserByCorreo(String correo) {
        return userEntityMapper.toUserModel(userPersistencePort.getUserByCorreo(correo));
    }
}
