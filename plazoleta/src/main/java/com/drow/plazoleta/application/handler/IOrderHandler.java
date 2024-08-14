package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.response.OrderResponseDto;
import com.drow.plazoleta.domain.model.OrderModel;
import org.springframework.data.domain.Page;

public interface IOrderHandler {
    void saveOrder(String token, String restaurantNit);

    Page<OrderResponseDto> findAllByStatus(String token, int page, int size, String status);

    Page<OrderResponseDto> assignEmployeeToOrder(String token, Integer orderId, int page, int size, String status);

    void readyOrder(String token, Integer orderId);

    void deliverOrder(String token, Integer orderId, Integer pin);
}
