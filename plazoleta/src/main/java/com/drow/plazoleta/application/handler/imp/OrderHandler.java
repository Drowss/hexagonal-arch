package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.response.OrderResponseDto;
import com.drow.plazoleta.application.handler.IOrderHandler;
import com.drow.plazoleta.application.mapper.IOrderRequestMapper;
import com.drow.plazoleta.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;

    @Override
    public void saveOrder(String token, String restaurantNit) {
        orderServicePort.saveOrder(token, restaurantNit);
    }

    @Override
    public Page<OrderResponseDto> findAllByStatus(String token, int page, int size, String status) {
        return orderServicePort.findAllByStatus(token, page, size, status).map(orderRequestMapper::toOrderResponseDto);
    }
}
