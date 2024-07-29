package com.drow.plazoleta.domain.model;

public class CategoryModel {

    private Integer id;
    private String name;

    public CategoryModel() {
    }

    public CategoryModel(String name) {
        this.name = name;
    }

    public CategoryModel(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
