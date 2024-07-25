package com.drow.user.application.handler;

import com.drow.user.application.dto.request.UserRequestDto;

public interface IUserHandler {

    void saveUser(UserRequestDto userRequestDto);
}
