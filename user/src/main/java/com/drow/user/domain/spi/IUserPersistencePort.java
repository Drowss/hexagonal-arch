package com.drow.user.domain.spi;

import com.drow.user.domain.model.UserModel;

public interface IUserPersistencePort {
    void saveUser(UserModel userModel);
    Boolean userExists(Integer documentoDeIdentidad);
}
