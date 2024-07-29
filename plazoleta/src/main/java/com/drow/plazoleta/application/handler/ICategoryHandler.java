package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.CategoryRequestDto;

public interface ICategoryHandler {
    void saveCategory(CategoryRequestDto categoryRequestDto);
}
