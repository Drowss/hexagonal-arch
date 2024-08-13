package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.PinUserModel;

public interface IUserPinPersistencePort {
    void savePin(PinUserModel pinUserModel);

    PinUserModel findByUserIdAndOrderId(Integer userId, Integer id);
}
