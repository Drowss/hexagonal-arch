package com.drow.trazabilidad.domain.model;

import java.time.LocalDate;

public class TraceabilityModel {
    private String id;
    private Integer orderId;
    private Integer customerId;
    private String customerEmail;
    private String currentStatus;
    private Integer employeeId;
    private String employeeEmail;
    private LocalDate orderStartDate;
    private LocalDate orderEndDate;

    public TraceabilityModel(String id, Integer orderId, Integer customerId, String customerEmail, String currentStatus, Integer employeeId, String employeeEmail, LocalDate orderStartDate, LocalDate orderEndDate) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.currentStatus = currentStatus;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
        this.orderStartDate = orderStartDate;
        this.orderEndDate = orderEndDate;
    }

    public TraceabilityModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public LocalDate getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(LocalDate orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public LocalDate getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(LocalDate orderEndDate) {
        this.orderEndDate = orderEndDate;
    }
}
