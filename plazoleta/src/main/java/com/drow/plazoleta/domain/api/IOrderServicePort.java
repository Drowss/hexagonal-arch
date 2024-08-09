package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.application.dto.response.OrderResponseDto;
import com.drow.plazoleta.domain.model.OrderModel;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    void saveOrder(String token, String restaurantNit);

    Page<OrderModel> findAllByStatus(String token, int page, int size, String status);

    Page<OrderModel> assignEmployeeToOrder(String token, Integer orderId, int page, int size, String status);
}
