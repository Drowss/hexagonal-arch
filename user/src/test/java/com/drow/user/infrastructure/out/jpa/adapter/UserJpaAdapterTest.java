package com.drow.user.infrastructure.out.jpa.adapter;

import com.drow.user.domain.model.Rol;
import com.drow.user.domain.model.UserModel;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import com.drow.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.drow.user.infrastructure.out.jpa.repository.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    private final UserEntity userEntity = new UserEntity();
    private final UserModel userModel = new UserModel();

    @BeforeEach
    void setUp() {
        userEntity.setDocumentoDeIdentidad(123456789);
        userEntity.setNombre("John");
        userEntity.setCorreo("example@gmail.com");
        userEntity.setClave("123456");
        userEntity.setRol(Rol.PROPIETARIO);
        userEntity.setCelular("123456");
        userEntity.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        userEntity.setApellido("Beamer");

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
    void UserJpaAdapter_SaveUser_ReturnVoid() {
        when(userEntityMapper.toEntity(any(UserModel.class))).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userEntityMapper.toUserModel(any(UserEntity.class))).thenReturn(userModel);
        UserModel savedUser = userJpaAdapter.saveUser(userModel);
        verify(userRepository, times(1)).save(userEntity);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getDocumentoDeIdentidad()).isEqualTo(userModel.getDocumentoDeIdentidad());
    }
}