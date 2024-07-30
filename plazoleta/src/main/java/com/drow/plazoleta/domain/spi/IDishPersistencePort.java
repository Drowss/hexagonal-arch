package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;

public interface IDishPersistencePort {
    DishModel saveDish(DishEntity dishEntity);
    DishEntity getDishById(Integer id);
}
