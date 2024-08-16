package com.drow.plazoleta.infrastructure.out.repository;

import com.drow.plazoleta.infrastructure.out.entity.OrderEntity;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByUserIdAndRestaurant(Integer userId, RestaurantEntity restaurant);
    Page<OrderEntity> findAllByStatusAndUserId(OrderStatus status, Integer cedula, Pageable pageable);

    List<OrderEntity> findAllByRestaurantNit(String restaurantNit);
}
