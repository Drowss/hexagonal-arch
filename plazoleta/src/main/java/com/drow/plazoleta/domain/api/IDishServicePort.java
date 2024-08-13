package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.application.dto.response.DishResponseDto;
import com.drow.plazoleta.domain.model.DishDomain;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.ModifyDishModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDishServicePort {
    void saveDish(DishModel dishModel, String token);

    void modifyDish(ModifyDishModel modifyDishModel);

    void toggleDish(Integer id, String token);

    Page<DishDomain> getDishesRestaurant(String nit, int page, int size, Integer categoryId);
}
