package com.drow.plazoleta.domain.model;


public class OrderItemModel {

    private Integer id;

    private OrderModel order;

    private DishDomain dish;

    private Integer quantity;

    public OrderItemModel(Integer id, OrderModel order, DishDomain dish, Integer quantity) {
        this.id = id;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
    }

    public OrderItemModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public DishDomain getDish() {
        return dish;
    }

    public void setDish(DishDomain dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
