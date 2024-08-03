package com.drow.user.domain.usecase;

import com.drow.user.application.dto.response.TokenResponseDto;
import com.drow.user.application.exception.IncorrectPassword;
import com.drow.user.application.jwt.JwtHandler;
import com.drow.user.domain.model.Rol;
import com.drow.user.domain.model.UserLoginModel;
import com.drow.user.domain.model.UserModel;
import com.drow.user.domain.spi.IUserPasswordEncrypter;
import com.drow.user.domain.spi.IUserPersistencePort;
import com.drow.user.infrastructure.exception.UserAlreadyExists;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private HttpServletResponse response;

    @Mock
    private IUserPasswordEncrypter userPasswordEncrypter;

    @Mock
    private JwtHandler jwtHandler;

    @InjectMocks
    private UserUseCase userUseCase;

    private final UserModel userModel = new UserModel();

    @BeforeEach
    void setUp() {
        userModel.setDocumentoDeIdentidad(123456789);
        userModel.setNombre("John");
        userModel.setCorreo("example@gmail.com");
        userModel.setClave("123456");
        userModel.setRol(Rol.PROPIETARIO);
        userModel.setCelular("123456");
        userModel.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        userModel.setApellido("Beamer");
    }

    @Test
    void userUseCase_SaveUser_ReturnVoid() {
        when(userPersistencePort.userExists(anyInt())).thenReturn(false);
        when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(userModel);

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));
    }

    @Test
    void userUseCase_SaveUser_ThrowUserAlreadyExists() {
        when(userPersistencePort.userExists(anyInt())).thenReturn(true);

        assertThrows(UserAlreadyExists.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void loginUser_shouldReturnTokenResponseDto() {
        // Arrange
        UserLoginModel userLoginModel = new UserLoginModel("test@example.com", "password");

        UserEntity userEntity = new UserEntity();
        userEntity.setCorreo("test@example.com");
        userEntity.setClave("encryptedPassword");
        userEntity.setDocumentoDeIdentidad(1234);

        when(userPersistencePort.getUserByCorreo("test@example.com")).thenReturn(userEntity);
        when(userPasswordEncrypter.checkPassword("password", "encryptedPassword")).thenReturn(true);
        when(jwtHandler.generateToken(any(Map.class), eq("test@example.com"))).thenReturn("generatedToken");

        // Act
        TokenResponseDto tokenResponseDto = userUseCase.loginUser(userLoginModel, response);

        // Assert
        assertNotNull(tokenResponseDto);
        assertEquals("generatedToken", tokenResponseDto.getToken());

        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void loginUser_shouldThrowIncorrectPasswordException() {
        // Arrange
        UserLoginModel userLoginModel = new UserLoginModel("test@example.com", "password");

        UserEntity userEntity = new UserEntity();
        userEntity.setCorreo("test@example.com");
        userEntity.setClave("encryptedPassword");

        when(userPersistencePort.getUserByCorreo("test@example.com")).thenReturn(userEntity);
        when(userPasswordEncrypter.checkPassword("password", "encryptedPassword")).thenReturn(false);

        // Act & Assert
        assertThrows(IncorrectPassword.class, () -> {
            userUseCase.loginUser(userLoginModel, response);
        });
    }
}