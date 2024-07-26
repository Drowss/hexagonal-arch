package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import com.drow.plazoleta.application.exception.UserNoPermissions;
import com.drow.plazoleta.application.handler.IRestaurantHandler;
import com.drow.plazoleta.application.mapper.IRestaurantRequestMapper;
import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.model.RestaurantModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie roleCookie = Arrays.stream(cookies)
                .filter(cookie -> "userRole".equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        if (roleCookie != null && "ADMINISTRADOR".equals(roleCookie.getValue())){
            RestaurantModel restaurantModel = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
            restaurantServicePort.saveRestaurant(restaurantModel);
        } else {
            throw new UserNoPermissions("No tienes permisos para realizar esta acción");
        }
    }
}
