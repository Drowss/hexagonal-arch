package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import com.drow.plazoleta.domain.model.RestaurantModel;

public interface IRestaurantServicePort {
    void saveRestaurant(RestaurantModel restaurantModel);

    RestaurantModel getRestaurantByNit(String restaurantNit);
}
