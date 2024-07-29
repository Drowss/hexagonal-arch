package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.ICategoryServicePort;
import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    @Override
    public void saveCategory(CategoryModel categoryModel) {
        categoryPersistencePort.saveCategory(categoryModel);
    }
}
