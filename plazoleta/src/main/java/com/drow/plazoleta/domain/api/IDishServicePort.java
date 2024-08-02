package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.ModifyDishModel;

public interface IDishServicePort {
    void saveDish(DishModel dishModel);

    void modifyDish(ModifyDishModel modifyDishModel);

    void toggleDish(Integer id, String token);
}
