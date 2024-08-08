package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.infrastructure.out.entity.OrderEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderPersistencePort {
    void saveOrder(OrderModel orderModel);
    List<OrderModel> findOrderEntityByUserIdAndRestaurant(Integer cedula, RestaurantModel restaurant);
    OrderModel findById(Integer id);

    List<OrderModel> findAllByUserIdAndStatus(OrderStatus orderStatus, Integer cedula, Pageable pageable);
}
