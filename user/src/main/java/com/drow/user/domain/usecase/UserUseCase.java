package com.drow.user.domain.usecase;

import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.UserModel;
import com.drow.user.domain.spi.IUserPersistencePort;
import com.drow.user.infrastructure.exception.UserAlreadyExists;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    @Override
    public void saveUser(UserModel userModel) {
        if (userPersistencePort.userExists(userModel.getDocumentoDeIdentidad())) throw new UserAlreadyExists("El usuario ya existe");
        userPersistencePort.saveUser(userModel);
    }
}
