package com.drow.mensajeria.domain.api;

import com.drow.mensajeria.application.dto.response.PinUserResponseDto;
import com.drow.mensajeria.domain.model.PinUserModel;
import org.springframework.http.ResponseEntity;

public interface IPinUserServicePort {
    void savePinUser(Integer userId, Integer orderId);

    PinUserModel findPinUser(Integer pin);
}
