package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.domain.model.DishModel;

public interface IDishServicePort {
    void saveDish(DishModel dishModel);
}
