package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.domain.model.OrderModel;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    void saveOrder(String token, String restaurantNit);

    Page<OrderModel> findAllByStatus(String token, int page, int size, String status);

    Page<OrderModel> assignEmployeeToOrder(String token, Integer orderId, int page, int size, String status);

    void readyOrder(String token, Integer orderId);

    void deliverOrder(String token, Integer orderId, Integer pin);

    void deleteOrder(String token, Integer orderId);
}
