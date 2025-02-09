package com.drow.plazoleta.domain.model;

import com.drow.plazoleta.domain.model.enums.OrderStatus;

import java.util.List;

public class OrderModel {
    private Integer id;
    private RestaurantModel restaurant;
    private Integer userId;

    private OrderStatus status;

    private List<OrderItemModel> items;

    private Integer employee;

    public OrderModel(Integer id, RestaurantModel restaurant, Integer userId, OrderStatus status, List<OrderItemModel> items, Integer employee) {
        this.id = id;
        this.restaurant = restaurant;
        this.userId = userId;
        this.status = status;
        this.items = items;
        this.employee = employee;
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

    public List<OrderItemModel> getItems() {
        return items;
    }

    public void setItems(List<OrderItemModel> items) {
        this.items = items;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }
}
