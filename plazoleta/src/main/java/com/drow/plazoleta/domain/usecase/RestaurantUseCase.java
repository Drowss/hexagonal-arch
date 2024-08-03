package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.exception.RestaurantAlreadyExists;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        if (Boolean.TRUE.equals(restaurantPersistencePort.restaurantExists(restaurantModel.getNit())))
            throw new RestaurantAlreadyExists("El restaurante ya existe");
        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }

    @Override
    public RestaurantModel getRestaurantByNit(String restaurantNit) {
        return restaurantPersistencePort.getRestaurantByNit(restaurantNit);
    }

    @Override
    public Page<RestaurantModel> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        return restaurantPersistencePort.findAll(pageable);
    }
}
