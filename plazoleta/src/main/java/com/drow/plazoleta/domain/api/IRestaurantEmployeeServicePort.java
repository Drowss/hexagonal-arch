package com.drow.plazoleta.domain.api;

import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;

public interface IRestaurantEmployeeServicePort {
    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel, String token);
}
