package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantModel;

import java.util.List;

public interface IOrderPersistencePort {
    void saveOrder(OrderModel orderModel);
    List<OrderModel> findOrderEntityByUserIdAndRestaurant(Integer cedula, RestaurantModel restaurant);
}
