package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;

public interface IOrderHandler {
    void saveOrder(String token, String restaurantNit);

    Page<OrderResponseDto> findAllByStatus(String token, int page, int size, String status);
}
