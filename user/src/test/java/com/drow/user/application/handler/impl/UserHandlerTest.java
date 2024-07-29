package com.drow.user.application.handler.impl;

import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.exception.UserUnderageException;
import com.drow.user.application.mapper.IUserRequestMapper;
import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.UserModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserHandler userHandler;

    private UserRequestDto userRequestDto;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .documentoDeIdentidad(12345678)
                .nombre("Juan")
                .apellido("Pérez")
                .celular("+1234567890123")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .correo("juan.perez@example.com")
                .clave("password123")
                .build();

        userModel = UserModel.builder()
                .documentoDeIdentidad(12345678)
                .nombre("Juan")
                .apellido("Pérez")
                .celular("+1234567890123")
                .rol(null)
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .correo("juan.perez@example.com")
                .clave("password123")
                .build();
    }

    @Test
    void userHandler_SaveUser_ReturnVoid() {
        when(userRequestMapper.toUserModel(any(UserRequestDto.class))).thenReturn(userModel);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userHandler.saveUser(userRequestDto));
    }

    @Test
    void userHandler_SaveUser_ThrowUserUnderageException() {
        userRequestDto.setFechaNacimiento(LocalDate.now());

        UserUnderageException exception = assertThrows(UserUnderageException.class, () -> userHandler.saveUser(userRequestDto));
        Assertions.assertThat(exception.getMessage()).isEqualTo("El usuario debe ser mayor de edad");
    }

}