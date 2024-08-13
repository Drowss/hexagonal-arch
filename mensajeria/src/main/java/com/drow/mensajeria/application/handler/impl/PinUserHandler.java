package com.drow.mensajeria.application.handler.impl;

import com.drow.mensajeria.application.handler.IPinUserHandler;
import com.drow.mensajeria.domain.api.IPinUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PinUserHandler implements IPinUserHandler {

    private final IPinUserServicePort pinUserServicePort;

    @Override
    public void savePinUser(Integer userId, Integer orderId) {
        pinUserServicePort.savePinUser(userId, orderId);
    }
}
