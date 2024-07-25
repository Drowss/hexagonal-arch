package com.drow.user.domain.api;

import com.drow.user.domain.model.UserModel;

public interface IUserServicePort {
    void saveUser(UserModel ownerModel);
}
