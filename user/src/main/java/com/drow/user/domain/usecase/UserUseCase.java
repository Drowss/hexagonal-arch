package com.drow.user.domain.usecase;

import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.model.UserModel;
import com.drow.user.domain.spi.IUserPersistencePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    @Override
    public void saveUser(UserModel userModel) {
        userPersistencePort.saveUser(userModel);
    }
}
