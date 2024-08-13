package com.drow.plazoleta.domain.spi;

public interface PinUserFeignPort {
    void savePinUser(Integer userId, Integer orderId);
}
