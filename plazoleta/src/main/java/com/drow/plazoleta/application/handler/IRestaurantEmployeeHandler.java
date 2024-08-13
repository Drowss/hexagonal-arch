package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;

public interface IRestaurantEmployeeHandler {
    void saveRestaurantEmployee(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto, String token);
}
