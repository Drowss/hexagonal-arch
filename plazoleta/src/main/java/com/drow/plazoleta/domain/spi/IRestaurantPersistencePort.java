package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantPersistencePort {
    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);

    Boolean restaurantExists(String nit);

    RestaurantModel getRestaurantByNit(String restaurantNit);

    Page<RestaurantModel> findAll(Pageable page);

    RestaurantModel findByCedula(String ownerDni);
}
