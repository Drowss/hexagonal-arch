package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderPersistencePort {
    OrderModel saveOrder(OrderModel orderModel);
    List<OrderModel> findOrderEntityByUserIdAndRestaurant(Integer cedula, RestaurantModel restaurant);
    OrderModel findById(Integer id);

    OrderModel findByIdIgnoreCycle(Integer id);

    List<OrderModel> findAllByUserIdAndStatus(OrderStatus orderStatus, Integer cedula, Pageable pageable);


    void deleteOrder(OrderModel orderModel);
}
