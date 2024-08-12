package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;

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

    @Test
    public void testFindAll() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        RestaurantModel restaurant1 = new RestaurantModel();
        restaurant1.setNombre("Restaurant A");
        RestaurantModel restaurant2 = new RestaurantModel();
        restaurant2.setNombre("Restaurant B");
        Page<RestaurantModel> restaurantPage = new PageImpl<>(Arrays.asList(restaurant1, restaurant2), pageable, 2);

        when(restaurantPersistencePort.findAll(pageable)).thenReturn(restaurantPage);

        // Act
        Page<RestaurantModel> result = restaurantUseCase.findAll(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("Restaurant A", result.getContent().get(0).getNombre());
        assertEquals("Restaurant B", result.getContent().get(1).getNombre());
        verify(restaurantPersistencePort, times(1)).findAll(pageable);
    }

}