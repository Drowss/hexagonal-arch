package com.drow.plazoleta.domain.dto;

import java.time.LocalDateTime;

public class OrderEfficiency {
    private Integer employeeId;
    private Integer orderId;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    public OrderEfficiency(Integer employeeId, Integer orderId, LocalDateTime orderDate, LocalDateTime deliveryDate) {
        this.employeeId = employeeId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }

    public OrderEfficiency() {
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
