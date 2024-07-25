package com.drow.user.domain.spi;

import com.drow.user.domain.model.UserModel;

public interface IUserPersistencePort {
    UserModel saveUser(UserModel userModel);
}
