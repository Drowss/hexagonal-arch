package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.domain.model.CategoryModel;

public interface ICategoryServicePort {
    void saveCategory(CategoryModel categoryModel);
}
