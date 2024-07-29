package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.exception.RestaurantAlreadyExists;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        if (Boolean.TRUE.equals(restaurantPersistencePort.restaurantExists(restaurantModel.getNit())))
            throw new RestaurantAlreadyExists("El restaurante ya existe");
        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }
}
