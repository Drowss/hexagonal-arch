package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {
    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);

    Boolean restaurantExists(String nit);

    RestaurantModel getRestaurantByNit(String restaurantNit);
}
