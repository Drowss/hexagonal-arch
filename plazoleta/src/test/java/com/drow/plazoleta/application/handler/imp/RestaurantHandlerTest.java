package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import com.drow.plazoleta.application.mapper.IRestaurantRequestMapper;
import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    private RestaurantModel restaurantModel;
    private RestaurantRequestDto restaurantRequestDto;

    @BeforeEach
    void setUp() {
        restaurantModel = RestaurantModel.builder()
                .nit("1234")
                .nombre("Restaurant")
                .direccion("Calle 123")
                .telefono("1234567")
                .urlLogo("https://www.google.com")
                .idPropietario(1)
                .build();

        restaurantRequestDto = RestaurantRequestDto.builder()
                .nit("1234")
                .nombre("Restaurant")
                .direccion("Calle 123")
                .telefono("1234567")
                .urlLogo("https://www.google.com")
                .idPropietario(1)
                .build();
    }

    @Test
    void restaurantHandler_SaveRestaurant_ReturnVoid() {
        // Act & Assert
        when(restaurantRequestMapper.toRestaurant(any(RestaurantRequestDto.class))).thenReturn(restaurantModel);
        Assertions.assertDoesNotThrow(() -> {
            restaurantHandler.saveRestaurant(restaurantRequestDto);
        });
        verify(restaurantServicePort, times(1)).saveRestaurant(restaurantModel);
    }

}