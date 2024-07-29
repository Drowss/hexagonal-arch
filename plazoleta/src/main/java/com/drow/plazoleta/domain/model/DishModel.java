package com.drow.plazoleta.domain.model;


public class DishModel {
    private String name;
    private Float price;
    private Boolean active;
    private String description;
    private String imageUrl;
    private Integer categoryId;
    private String restaurantNit;

    public DishModel(String name, Float price, Boolean active, String description, String imageUrl, Integer categoryId, String restaurantNit) {
        this.name = name;
        this.price = price;
        this.active = active;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.restaurantNit = restaurantNit;
    }

    public DishModel(String name, Float price, String description, String imageUrl, Integer categoryId, String restaurantNit) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.restaurantNit = restaurantNit;
    }

    public DishModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getRestaurantNit() {
        return restaurantNit;
    }

    public void setRestaurantNit(String restaurantNit) {
        this.restaurantNit = restaurantNit;
    }
}
