package com.drow.plazoleta.application.handler;

import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
}
