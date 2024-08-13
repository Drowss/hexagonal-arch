package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.dto.request.ModifyDishRequestDto;
import com.drow.plazoleta.application.dto.response.DishResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto, String token);

    void modifyDish(ModifyDishRequestDto modifyDishRequestDto);

    void toggleDish(Integer id, String token);

    Page<DishResponseDto> getDishesRestaurant(String nit, int page, int size, Integer categoryId);
}
