package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @Test
    void restaurantUseCase_SaveRestaurant_ReturnVoid() {
        // Arrange
        RestaurantModel restaurantModel = RestaurantModel.builder()
                .nit("1234")
                .nombre("Restaurant")
                .direccion("Calle 123")
                .telefono("1234567")
                .urlLogo("https://www.google.com")
                .nit("413213")
                .build();
        // Act
        restaurantUseCase.saveRestaurant(restaurantModel);

        // Assert
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurantModel);
    }

}