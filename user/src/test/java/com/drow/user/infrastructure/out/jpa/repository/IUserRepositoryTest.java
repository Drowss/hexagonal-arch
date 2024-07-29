package com.drow.user.infrastructure.out.jpa.repository;

import com.drow.user.domain.model.Rol;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class IUserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    void userRepository_SaveUser_ReturnUserModel() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setDocumentoDeIdentidad(123456789);
        userEntity.setNombre("John");
        userEntity.setCorreo("example@gmail.com");
        userEntity.setClave("123456");
        userEntity.setRol(Rol.PROPIETARIO);
        userEntity.setCelular("123456");
        userEntity.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        userEntity.setApellido("Beamer");

        UserEntity savedUser = userRepository.save(userEntity);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getDocumentoDeIdentidad()).isEqualTo(userEntity.getDocumentoDeIdentidad());
    }
}