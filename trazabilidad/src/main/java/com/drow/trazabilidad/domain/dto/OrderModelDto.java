package com.drow.trazabilidad.domain.dto;


public class OrderModelDto {
    private Integer id;
    private RestaurantModelDto restaurant;
    private Integer userId;

    private OrderStatus status;

    private Integer employee;

    public OrderModelDto(Integer id, RestaurantModelDto restaurant, Integer userId, OrderStatus status, Integer employee) {
        this.id = id;
        this.restaurant = restaurant;
        this.userId = userId;
        this.status = status;
        this.employee = employee;
    }

    public OrderModelDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantModelDto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModelDto restaurant) {
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

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }
}
