package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.application.dto.request.OrderRequestDto;

public interface IOrderServicePort {
    void saveOrder(String token, String restaurantNit);
}
