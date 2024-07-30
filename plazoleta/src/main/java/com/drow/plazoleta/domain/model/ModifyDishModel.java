package com.drow.plazoleta.domain.model;

public class ModifyDishModel {
    private Integer id;
    private Float price;
    private String description;

    public ModifyDishModel(Integer id, Float price, String description) {
        this.id = id;
        this.price = price;
        this.description = description;
    }

    public ModifyDishModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
