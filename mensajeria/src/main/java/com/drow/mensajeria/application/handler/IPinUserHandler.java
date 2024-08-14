package com.drow.mensajeria.application.handler;

import com.drow.mensajeria.application.dto.response.PinUserResponseDto;

public interface IPinUserHandler {
    void savePinUser(Integer userId, Integer orderId);

    PinUserResponseDto findPinUser(Integer pin);
}
