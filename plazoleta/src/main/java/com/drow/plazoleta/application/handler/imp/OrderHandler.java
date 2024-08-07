package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.handler.IOrderHandler;
import com.drow.plazoleta.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;

    @Override
    public void saveOrder(String token, String restaurantNit) {
        orderServicePort.saveOrder(token, restaurantNit);
    }
}
