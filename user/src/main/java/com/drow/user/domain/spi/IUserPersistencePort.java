package com.drow.user.domain.spi;

import com.drow.user.domain.model.UserModel;
import com.drow.user.infrastructure.out.jpa.entity.UserEntity;

public interface IUserPersistencePort {
    UserModel saveUser(UserModel userModel);
    Boolean userExists(Integer documentoDeIdentidad);
    UserEntity getUserByCorreo(String correo);
}
