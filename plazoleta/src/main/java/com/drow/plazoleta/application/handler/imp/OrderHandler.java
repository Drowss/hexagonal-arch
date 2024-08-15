package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.response.OrderResponseDto;
import com.drow.plazoleta.application.handler.IOrderHandler;
import com.drow.plazoleta.application.mapper.IOrderRequestMapper;
import com.drow.plazoleta.domain.api.IOrderServicePort;
import com.drow.plazoleta.domain.model.OrderModel;
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

    @Override
    public Page<OrderResponseDto> assignEmployeeToOrder(String token, Integer orderId, int page, int size, String status) {
        return orderServicePort.assignEmployeeToOrder(token, orderId, page, size, status).map(orderRequestMapper::toOrderResponseDto);
    }

    @Override
    public void readyOrder(String token, Integer orderId) {
        orderServicePort.readyOrder(token, orderId);
    }

    @Override
    public void deliverOrder(String token, Integer orderId, Integer pin) {
        orderServicePort.deliverOrder(token, orderId, pin);
    }

    @Override
    public void deleteOrder(String token, Integer orderId) {
        orderServicePort.deleteOrder(token, orderId);
    }
}
