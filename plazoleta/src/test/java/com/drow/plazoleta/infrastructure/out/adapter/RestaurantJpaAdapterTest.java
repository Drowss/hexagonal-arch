package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IRestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantJpaAdapterTest {
    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    @Test
    void RestaurantJpaAdapter_SaveRestaurant_ReturnRestaurantModel() {
        // Arrange
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setNit("1234");
        restaurantEntity.setNombre("Restaurant");
        restaurantEntity.setDireccion("Calle 123");
        restaurantEntity.setTelefono("1234567");
        restaurantEntity.setUrlLogo("https://www.google.com");
        restaurantEntity.setNit("413213");
        RestaurantModel restaurantModel = RestaurantModel.builder()
                .nit("1234")
                .nombre("Restaurant")
                .direccion("Calle 123")
                .telefono("1234567")
                .urlLogo("https://www.google.com")
                .nit("413213")
                .build();
        // Act
        when(restaurantEntityMapper.toEntity(any(RestaurantModel.class))).thenReturn(restaurantEntity);
        when(restaurantEntityMapper.toModel(any(RestaurantEntity.class))).thenReturn(restaurantModel);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);
        RestaurantModel savedRestaurant = restaurantJpaAdapter.saveRestaurant(restaurantModel);
        verify(restaurantRepository, Mockito.times(1)).save(restaurantEntity);
        // Assert
        Assertions.assertThat(savedRestaurant).isNotNull();
    }

}