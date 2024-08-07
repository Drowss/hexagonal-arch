package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.domain.model.OrderItemModel;

public interface IOrderItemServicePort {
    void saveOrderDishDetail(OrderItemModel orderItemModel);
}
