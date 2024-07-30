package com.drow.user.domain.usecase;

import com.drow.user.application.exception.IncorrectPassword;
import com.drow.user.application.jwt.IJwtHandler;
import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.UserLoginModel;
import com.drow.user.domain.model.UserModel;
import com.drow.user.domain.spi.IUserPersistencePort;
import com.drow.user.infrastructure.exception.UserAlreadyExists;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.Map;

@AllArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final IJwtHandler jwtHandler;

    @Override
    public void saveUser(UserModel userModel) {
        if (Boolean.TRUE.equals(userPersistencePort.userExists(userModel.getDocumentoDeIdentidad())))
            throw new UserAlreadyExists("El usuario ya existe");
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public void loginUser(UserLoginModel userLoginModel, HttpServletResponse response) {
        UserEntity userEntity = userPersistencePort.getUserByCorreo(userLoginModel.getCorreo());
        System.out.println("en usercase exitoso " + userEntity.getCorreo());
        if (!passwordEncoder.matches(userLoginModel.getClave(), userEntity.getClave()))
            throw new IncorrectPassword("La contraseña es incorrecta");
        Map<String, Object> extraClaims = Map.of("useremail", userEntity.getCorreo());
        String token = jwtHandler.generateToken(extraClaims);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(false);
        cookie.setMaxAge((int) Duration.ofDays(1).toSeconds());
        response.addCookie(cookie);
    }
}
