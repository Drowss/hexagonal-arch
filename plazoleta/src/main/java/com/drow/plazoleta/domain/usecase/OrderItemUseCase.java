package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IOrderItemServicePort;
import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.spi.IOrderItemPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderItemUseCase implements IOrderItemServicePort {

    private final IOrderItemPersistencePort orderItemPersistencePort;

    @Override
    public void saveOrderDishDetail(OrderItemModel orderItemModel) {
        if (Boolean.TRUE.equals(orderItemPersistencePort.existsByOrderIdAndDishId(orderItemModel.getOrder().getId(), orderItemModel.getDish().getId()))) {
            OrderItemModel orderItem = orderItemPersistencePort.findByOrderIdAndDishId(orderItemModel.getOrder().getId(), orderItemModel.getDish().getId());
            orderItem.setOrder(orderItemModel.getOrder());
            orderItem.setQuantity(orderItemModel.getQuantity());
            orderItemPersistencePort.save(orderItem);
            return;
        }
        orderItemPersistencePort.save(orderItemModel);
    }
}
