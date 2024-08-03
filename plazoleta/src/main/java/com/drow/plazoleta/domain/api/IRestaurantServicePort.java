package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import com.drow.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;

public interface IRestaurantServicePort {
    void saveRestaurant(RestaurantModel restaurantModel);

    RestaurantModel getRestaurantByNit(String restaurantNit);

    Page<RestaurantModel> findAll(int page, int size);
}
