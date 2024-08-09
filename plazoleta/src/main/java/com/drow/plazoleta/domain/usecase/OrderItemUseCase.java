package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IOrderItemServicePort;
import com.drow.plazoleta.domain.exception.DishNotOwnedRestaurant;
import com.drow.plazoleta.domain.model.DishDomain;
import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import com.drow.plazoleta.domain.spi.IOrderItemPersistencePort;
import com.drow.plazoleta.domain.spi.IOrderPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderItemUseCase implements IOrderItemServicePort {

    private final IOrderItemPersistencePort orderItemPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;

    @Override
    public void saveOrderDishDetail(OrderItemModel orderItemModel) {
        if (Boolean.TRUE.equals(orderItemPersistencePort.existsByOrderIdAndDishId(orderItemModel.getOrder().getId(), orderItemModel.getDish().getId()))) {
            OrderItemModel orderItem = orderItemPersistencePort.findByOrderIdAndDishId(orderItemModel.getOrder().getId(), orderItemModel.getDish().getId());
            orderItem.setOrder(orderItemModel.getOrder());
            orderItem.setQuantity(orderItemModel.getQuantity());
            orderItemPersistencePort.save(orderItem);
            return;
        }
        OrderModel order = orderPersistencePort.findByIdIgnoreCycle(orderItemModel.getOrder().getId());
        DishEntity dishDomain = dishPersistencePort.getDishById(orderItemModel.getDish().getId());
        System.out.println(dishDomain.getRestaurant().getNit() + " " + order.getRestaurant().getNit());
        if (!dishDomain.getRestaurant().getNit().equals(order.getRestaurant().getNit())) {
            throw new DishNotOwnedRestaurant("El plato no pertenece al restaurante de la orden");
        }
        orderItemPersistencePort.save(orderItemModel);
    }
}
