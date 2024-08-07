package com.drow.plazoleta.domain.model;

public class DishDomain {
    private Integer id;
    private String name;
    private Float price;
    private Boolean active;
    private String description;
    private String imageUrl;
    private CategoryModel category;
    private RestaurantModel restaurant;

    public DishDomain(String name, Float price, Boolean active, String description, String imageUrl, CategoryModel category, RestaurantModel restaurant) {
        this.name = name;
        this.price = price;
        this.active = active;
        this.description = description;
        this.imageUrl = imageUrl;
        this.restaurant = restaurant;
        this.category = category;
    }

    public DishDomain() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }
}
