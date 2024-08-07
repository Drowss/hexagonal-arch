package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.OrderDishRequestDto;
import com.drow.plazoleta.application.handler.IOrderItemHandler;
import com.drow.plazoleta.application.mapper.IOrderItemRequestMapper;
import com.drow.plazoleta.domain.api.IOrderItemServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemHandler implements IOrderItemHandler {

    private final IOrderItemServicePort orderDishServicePort;
    private final IOrderItemRequestMapper orderDishRequestMapper;

    @Override
    public void saveOrderDishDetail(OrderDishRequestDto orderRequestDto) {
        orderDishServicePort.saveOrderDishDetail(orderDishRequestMapper.toOrderDish(orderRequestDto));
    }
}
