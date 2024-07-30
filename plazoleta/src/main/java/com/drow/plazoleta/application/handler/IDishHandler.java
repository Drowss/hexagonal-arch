package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.dto.request.ModifyDishRequestDto;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);

    void modifyDish(ModifyDishRequestDto modifyDishRequestDto);
}
