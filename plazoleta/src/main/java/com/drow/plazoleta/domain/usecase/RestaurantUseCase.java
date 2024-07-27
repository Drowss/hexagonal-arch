package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }
}
