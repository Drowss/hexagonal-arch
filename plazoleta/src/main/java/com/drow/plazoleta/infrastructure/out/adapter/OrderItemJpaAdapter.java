package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.spi.IOrderItemPersistencePort;
import com.drow.plazoleta.infrastructure.out.mapper.IOrderItemEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IOrderItemRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderItemJpaAdapter implements IOrderItemPersistencePort {

    private final IOrderItemRepository orderItemRepository;
    private final IOrderItemEntityMapper orderItemMapper;

    @Override
    public void save(OrderItemModel orderItemModel) {
        orderItemRepository.save(orderItemMapper.toEntity(orderItemModel));
    }

    @Override
    public Boolean existsByOrderIdAndDishId(Integer orderId, Integer dishId) {
        return orderItemRepository.existsByOrder_IdAndDish_Id(orderId, dishId);
    }

    @Override
    public OrderItemModel findByOrderIdAndDishId(Integer id, Integer id1) {
        return orderItemMapper.toModel(orderItemRepository.findByOrder_IdAndDish_Id(id, id1));
    }

    @Override
    public List<OrderItemModel> findAllByOrderId(Integer id) {
        return orderItemRepository.findAllByOrderId(id).stream().map(orderItemMapper::toModel).toList();
    }

    @Override
    public OrderItemModel findById(Integer id) {
        return orderItemRepository.findById(id).map(orderItemMapper::toModel).orElse(null);
    }
}
