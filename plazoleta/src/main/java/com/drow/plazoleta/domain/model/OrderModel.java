package com.drow.plazoleta.domain.model;

import com.drow.plazoleta.infrastructure.out.entity.OrderItemEntity;
import com.drow.plazoleta.domain.model.enums.OrderStatus;

import java.util.List;

public class OrderModel {
    private Integer id;
    private RestaurantModel restaurant;
    private Integer userId;

    private OrderStatus status;

    private List<OrderItemEntity> items;

    public OrderModel(Integer id, RestaurantModel restaurant, Integer userId, OrderStatus status, List<OrderItemEntity> items) {
        this.id = id;
        this.restaurant = restaurant;
        this.userId = userId;
        this.status = status;
        this.items = items;
    }

    public OrderModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }
}
