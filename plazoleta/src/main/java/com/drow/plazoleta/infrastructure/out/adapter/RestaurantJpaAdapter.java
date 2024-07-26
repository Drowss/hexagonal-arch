package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurantModel));
    }
}
