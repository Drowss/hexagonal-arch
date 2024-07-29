package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.application.exception.CategoryDoesntExist;
import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.spi.ICategoryPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.CategoryEntity;
import com.drow.plazoleta.infrastructure.out.mapper.ICategoryEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IDishEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.ICategoryRepository;
import com.drow.plazoleta.infrastructure.out.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public CategoryModel saveCategory(CategoryModel categoryModel) {
        CategoryEntity categoryEntity = categoryRepository.save(categoryEntityMapper.toEntity(categoryModel));
        return categoryEntityMapper.toModel(categoryEntity);
    }

    @Override
    public CategoryModel getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryEntityMapper::toModel)
                .orElseThrow(() -> new CategoryDoesntExist("Category not found"));
    }
}
