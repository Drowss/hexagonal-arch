package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.DishModel;

public interface IDishPersistencePort {
    DishModel saveDish(DishModel dishModel);
}
