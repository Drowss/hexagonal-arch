package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.OrderDishRequestDto;

public interface IOrderItemHandler {
    void saveOrderDishDetail(OrderDishRequestDto orderRequestDto);
}
