package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.domain.spi.IOrderPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.OrderEntity;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import com.drow.plazoleta.infrastructure.out.mapper.IOrderEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

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

    @Override
    public OrderModel findById(Integer id) {
        return orderMapper.toModel(orderRepository.findById(id).orElse(null));
    }

    @Override
    public OrderModel findByIdIgnoreCycle(Integer id) {
        return orderMapper.toModelList(orderRepository.findById(id).orElse(null));
    }

    @Override
    public List<OrderModel> findAllByUserIdAndStatus(OrderStatus orderStatus, Integer cedula, Pageable pageable) {
        List<OrderEntity> orderEntityList = orderRepository.findAllByStatusAndUserId(orderStatus, cedula, pageable).toList();
        return orderEntityList.stream().map(orderMapper::toModelList).toList();
    }


}
