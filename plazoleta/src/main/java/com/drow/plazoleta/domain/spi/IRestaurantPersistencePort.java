package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {
    void saveRestaurant(RestaurantModel restaurantModel);
}
