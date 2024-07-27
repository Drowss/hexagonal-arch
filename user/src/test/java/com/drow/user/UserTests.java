package com.drow.user;

import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.exception.UserUnderageException;
import com.drow.user.application.handler.impl.UserHandler;
import com.drow.user.application.mapper.IUserRequestMapper;
import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.UserModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserTests {

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
    private Validator validator;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        userRequestDto.setClave("password123");

        userModel = new UserModel();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void saveUser_withValidUser_shouldSaveUser() {
        when(userRequestMapper.toUserModel(userRequestDto)).thenReturn(userModel);
        when(passwordEncoder.encode(userRequestDto.getClave())).thenReturn("encodedPassword");

        userHandler.saveUser(userRequestDto);

        verify(userRequestMapper, times(1)).toUserModel(userRequestDto);
        verify(passwordEncoder, times(1)).encode(userRequestDto.getClave());
        verify(userServicePort, times(1)).saveUser(userModel);
    }

    @Test
    void saveUser_withUnderageUser_shouldThrowUserUnderageException() {
        userRequestDto.setFechaNacimiento(LocalDate.now().minusYears(17));

        assertThrows(UserUnderageException.class, () -> {
            userHandler.saveUser(userRequestDto);
        });

        verify(userRequestMapper, times(0)).toUserModel(any(UserRequestDto.class));
        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userServicePort, times(0)).saveUser(any(UserModel.class));
    }

    @Test
    void whenAllFieldsAreValid_thenNoConstraintViolations() {
        UserRequestDto dto = UserRequestDto.builder()
                .correo("example@gmail.com")
                .documentoDeIdentidad(123)
                .celular("321")
                .fechaNacimiento(LocalDate.of(2000, 1, 1))
                .apellido("Doe")
                .clave("323")
                .nombre("John")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
