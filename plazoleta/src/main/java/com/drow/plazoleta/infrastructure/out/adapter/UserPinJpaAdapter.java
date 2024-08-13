package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.model.PinUserModel;
import com.drow.plazoleta.domain.spi.IUserPinPersistencePort;
import com.drow.plazoleta.infrastructure.out.mapper.IPinUserEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IPinUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPinJpaAdapter implements IUserPinPersistencePort {

    private final IPinUserRepository pinUserRepository;
    private final IPinUserEntityMapper pinUserEntityMapper;

    @Override
    public void savePin(PinUserModel pinUserModel) {
        pinUserRepository.save(pinUserEntityMapper.toEntity(pinUserModel));
    }

    @Override
    public PinUserModel findByUserIdAndOrderId(Integer userId, Integer orderId) {
        return pinUserEntityMapper.toModel(pinUserRepository.findByUserIdAndOrderId(userId, orderId));
    }
}
