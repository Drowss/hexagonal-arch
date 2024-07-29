package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.DishModel;

public interface ICategoryPersistencePort {
    CategoryModel saveCategory(CategoryModel categoryModel);

    CategoryModel getCategoryById(Integer categoryId);
}
