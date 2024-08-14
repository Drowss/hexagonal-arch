package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.dto.PinUserResponseDto;

public interface PinUserFeignPort {
    void savePinUser(Integer userId, Integer orderId);
    PinUserResponseDto findPinUser(Integer pin);
}
