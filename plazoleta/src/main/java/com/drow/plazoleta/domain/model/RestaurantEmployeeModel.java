package com.drow.plazoleta.domain.model;

public class RestaurantEmployeeModel {
    private Integer id;
    private String restaurantNit;
    private Integer employeeId;

    public RestaurantEmployeeModel(Integer id, String restaurantNit, Integer employeeId) {
        this.id = id;
        this.restaurantNit = restaurantNit;
        this.employeeId = employeeId;
    }

    public RestaurantEmployeeModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRestaurantNit() {
        return restaurantNit;
    }

    public void setRestaurantNit(String restaurantNit) {
        this.restaurantNit = restaurantNit;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
