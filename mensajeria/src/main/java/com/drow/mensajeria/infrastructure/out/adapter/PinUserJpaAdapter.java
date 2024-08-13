package com.drow.mensajeria.infrastructure.out.adapter;

import com.drow.mensajeria.domain.model.PinUserModel;
import com.drow.mensajeria.domain.spi.IPinUserPersistencePort;
import com.drow.mensajeria.infrastructure.out.mapper.IPinUserEntityMapper;
import com.drow.mensajeria.infrastructure.out.repository.IPinUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PinUserJpaAdapter implements IPinUserPersistencePort {

    private final IPinUserRepository pinUserRepository;
    private final IPinUserEntityMapper pinUserEntityMapper;

    @Override
    public void savePinUser(PinUserModel pinUserModel) {
        pinUserRepository.save(pinUserEntityMapper.toEntity(pinUserModel));
    }

    @Override
    public PinUserModel findByUserIdAndOrderId(Integer userId, Integer orderId) {
        return pinUserEntityMapper.toModel(pinUserRepository.findByUserIdAndOrderId(userId, orderId));
    }
}
