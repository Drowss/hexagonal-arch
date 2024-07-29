package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.DishRequestDto;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);
}
