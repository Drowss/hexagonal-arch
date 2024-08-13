package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.drow.plazoleta.application.handler.IRestaurantEmployeeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant-employee")
@RequiredArgsConstructor
public class RestaurantEmployeeRestController {
    private final IRestaurantEmployeeHandler restaurantEmployeeHandler;

    @PostMapping("/save")
    public void saveRestaurantEmployee(@RequestBody RestaurantEmployeeRequestDto restaurantEmployeeModelDto, @CookieValue("token") String token) {
        restaurantEmployeeHandler.saveRestaurantEmployee(restaurantEmployeeModelDto, token);
    }
}
