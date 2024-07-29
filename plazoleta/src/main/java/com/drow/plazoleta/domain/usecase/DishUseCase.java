package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    @Override
    public void saveDish(DishModel dishModel) {
        dishPersistencePort.saveDish(dishModel);
    }
}
