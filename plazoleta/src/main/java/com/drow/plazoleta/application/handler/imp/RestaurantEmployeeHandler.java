package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.drow.plazoleta.application.handler.IRestaurantEmployeeHandler;
import com.drow.plazoleta.application.mapper.IRestaurantEmployeeRequestMapper;
import com.drow.plazoleta.domain.api.IRestaurantEmployeeServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RestaurantEmployeeHandler implements IRestaurantEmployeeHandler {

    private final IRestaurantEmployeeServicePort restaurantEmployeeServicePort;
    private final IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;
    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeRequestDto restaurantEmployeeModelDto, String token) {
        restaurantEmployeeServicePort.saveRestaurantEmployee(restaurantEmployeeRequestMapper.toModel(restaurantEmployeeModelDto), token);
    }
}
