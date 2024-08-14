package com.drow.mensajeria.domain.spi;

import com.drow.mensajeria.domain.model.PinUserModel;

public interface IPinUserPersistencePort {
    void savePinUser(PinUserModel pinUserModel);

    PinUserModel findByUserIdAndOrderId(Integer userId, Integer orderId);

    PinUserModel findByPin(Integer pin);
}
