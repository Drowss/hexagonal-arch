package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.CategoryRequestDto;
import com.drow.plazoleta.application.handler.ICategoryHandler;
import com.drow.plazoleta.application.mapper.ICategoryRequestMapper;
import com.drow.plazoleta.domain.api.ICategoryServicePort;
import com.drow.plazoleta.domain.model.CategoryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper restaurantRequestMapper;

    @Override
    public void saveCategory(CategoryRequestDto categoryRequestDto) {
        CategoryModel categoryModel = restaurantRequestMapper.toCategory(categoryRequestDto);
        categoryServicePort.saveCategory(categoryModel);
    }
}
