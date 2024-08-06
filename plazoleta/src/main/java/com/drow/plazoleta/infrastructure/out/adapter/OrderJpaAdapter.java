package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IOrderPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import com.drow.plazoleta.infrastructure.out.mapper.IOrderEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderMapper;
    private final IRestaurantEntityMapper restaurantMapper;

    @Override
    public void saveOrder(OrderModel orderModel) {
        orderRepository.save(orderMapper.toEntity(orderModel));
    }

    @Override
    public List<OrderModel> findOrderEntityByUserIdAndRestaurant(Integer cedula, RestaurantModel restaurant) {
        RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurant);
        return orderRepository.findAllByUserIdAndRestaurant(cedula, restaurantEntity).stream().map(orderMapper::toModel).toList();
    }


}
