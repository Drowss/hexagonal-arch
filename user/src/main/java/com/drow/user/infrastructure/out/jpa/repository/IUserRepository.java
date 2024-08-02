package com.drow.user.infrastructure.out.jpa.repository;

import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByCorreo(String correo);
    Optional<UserEntity> findByNombre(String nombre);
}
