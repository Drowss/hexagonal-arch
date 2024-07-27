package com.drow.plazoleta;

import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import com.drow.plazoleta.application.handler.imp.RestaurantHandler;
import com.drow.plazoleta.application.mapper.IRestaurantRequestMapper;
import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;


@SpringBootTest
class RestaurantTests {

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    private RestaurantRequestDto restaurantRequestDto;

    private RestaurantModel restaurantModel;

    @BeforeEach
    void setUp() {
        restaurantRequestDto = new RestaurantRequestDto();
        restaurantModel = new RestaurantModel();
    }

    @Test
    void saveRestaurant_withValidRestaurant_shouldSaveRestaurant() {
        when(restaurantRequestMapper.toRestaurant(restaurantRequestDto)).thenReturn(restaurantModel);

        restaurantHandler.saveRestaurant(restaurantRequestDto);

        verify(restaurantServicePort, times(1)).saveRestaurant(restaurantModel);
        verify(restaurantRequestMapper, times(1)).toRestaurant(restaurantRequestDto);
    }

}
