package com.drow.user.domain.usecase;

import com.drow.user.domain.model.Rol;
import com.drow.user.domain.model.UserModel;
import com.drow.user.domain.spi.IUserPersistencePort;
import com.drow.user.infrastructure.exception.UserAlreadyExists;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

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
}