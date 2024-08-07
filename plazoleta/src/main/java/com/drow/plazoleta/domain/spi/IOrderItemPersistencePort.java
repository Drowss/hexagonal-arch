package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.OrderItemModel;

public interface IOrderItemPersistencePort {
    void save(OrderItemModel orderItemModel);
    Boolean existsByOrderIdAndDishId(Integer orderId, Integer dishId);

    OrderItemModel findByOrderIdAndDishId(Integer id, Integer id1);
}
