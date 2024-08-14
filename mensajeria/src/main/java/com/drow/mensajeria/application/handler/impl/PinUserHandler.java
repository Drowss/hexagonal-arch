package com.drow.mensajeria.application.handler.impl;

import com.drow.mensajeria.application.dto.response.PinUserResponseDto;
import com.drow.mensajeria.application.handler.IPinUserHandler;
import com.drow.mensajeria.application.mapper.PinUserMapper;
import com.drow.mensajeria.domain.api.IPinUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PinUserHandler implements IPinUserHandler {

    private final IPinUserServicePort pinUserServicePort;
    private final PinUserMapper pinUserMapper;

    @Override
    public void savePinUser(Integer userId, Integer orderId) {
        pinUserServicePort.savePinUser(userId, orderId);
    }

    @Override
    public PinUserResponseDto findPinUser(Integer pin) {
        return pinUserMapper.toResponse(pinUserServicePort.findPinUser(pin));
    }
}
