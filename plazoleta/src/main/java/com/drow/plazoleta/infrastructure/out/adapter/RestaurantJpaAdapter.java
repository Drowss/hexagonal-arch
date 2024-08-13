package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.application.exception.RestaurantDoesntExist;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public RestaurantModel saveRestaurant(RestaurantModel restaurantModel) {
        RestaurantEntity restaurantEntity = restaurantRepository.save(restaurantEntityMapper.toEntity(restaurantModel));
        return restaurantEntityMapper.toModel(restaurantEntity);
    }

    @Override
    public Boolean restaurantExists(String nit) {
        return restaurantRepository.existsById(nit);
    }

    @Override
    public RestaurantModel getRestaurantByNit(String restaurantNit) {
        return restaurantRepository.findById(restaurantNit)
                .map(restaurantEntityMapper::toModel)
                .orElseThrow(() -> new RestaurantDoesntExist("Restaurant not found"));
    }

    @Override
    public Page<RestaurantModel> findAll(Pageable page) {
        return restaurantRepository.findAll(page).map(restaurantEntityMapper::toModel);
    }

    @Override
    public RestaurantModel findByCedula(String ownerDni) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByCedulaPropietario(ownerDni);
        return restaurantEntityMapper.toModel(restaurantEntity);
    }
}
