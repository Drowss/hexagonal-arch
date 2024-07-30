package com.drow.user.infrastructure.out.jpa.adapter;

import com.drow.user.domain.model.UserModel;
import com.drow.user.domain.spi.IUserPersistencePort;
import com.drow.user.infrastructure.exception.UserAlreadyExists;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;
import com.drow.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.drow.user.infrastructure.out.jpa.repository.IUserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UserModel saveUser(UserModel userModel) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(userModel));
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public Boolean userExists(Integer documentoDeIdentidad) {
        return userRepository.existsById(documentoDeIdentidad);
    }

    @Override
    public UserEntity getUserByCorreo(String correo) {
        return userRepository.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));
    }

}
