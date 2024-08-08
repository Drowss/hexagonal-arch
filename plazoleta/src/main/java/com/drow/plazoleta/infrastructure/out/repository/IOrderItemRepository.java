package com.drow.plazoleta.infrastructure.out.repository;

import com.drow.plazoleta.infrastructure.out.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface IOrderItemRepository extends JpaRepository<OrderItemEntity, Integer>  {
    Boolean existsByOrder_IdAndDish_Id(Integer orderId, Integer dishId);

    OrderItemEntity findByOrder_IdAndDish_Id(Integer orderId, Integer dishId);

    List<OrderItemEntity> findAllByOrderId(Integer orderId);
}
