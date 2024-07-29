package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.domain.model.CategoryModel;

public interface ICategoryServicePort {
    CategoryModel saveCategory(CategoryModel categoryModel);

    CategoryModel getCategoryById(Integer categoryId);
}
