package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.dto.PinUserResponseDto;
import com.drow.plazoleta.domain.spi.PinUserFeignPort;
import com.drow.plazoleta.infrastructure.out.feign.PinUserFeignClientAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PinUserFeignAdapter implements PinUserFeignPort {

    private final PinUserFeignClientAdapter pinUserFeignClientAdapter;
    @Override
    public void savePinUser(Integer userId, Integer orderId) {
        pinUserFeignClientAdapter.savePinUser(userId, orderId);
    }

    @Override
    public PinUserResponseDto findPinUser(Integer pin) {
        return pinUserFeignClientAdapter.findPinUser(pin);
    }
}
