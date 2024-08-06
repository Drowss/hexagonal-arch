package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.OrderRequestDto;

public interface IOrderHandler {
    void saveOrder(String token, String restaurantNit);
}
