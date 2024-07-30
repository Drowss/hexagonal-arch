package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.ICategoryPersistencePort;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import com.drow.plazoleta.infrastructure.out.mapper.ICategoryEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveDish(DishModel dishModel) {
        CategoryModel categoryModel = categoryPersistencePort.getCategoryById(dishModel.getCategoryId());
        RestaurantModel restaurantModel = restaurantPersistencePort.getRestaurantByNit(dishModel.getRestaurantNit());
        DishEntity dishEntity = new DishEntity();
        dishEntity.setName(dishModel.getName());
        dishEntity.setPrice(dishModel.getPrice());
        dishEntity.setCategory(categoryEntityMapper.toEntity(categoryModel));
        dishEntity.setRestaurant(restaurantEntityMapper.toEntity(restaurantModel));
        dishEntity.setDescription(dishModel.getDescription());
        dishEntity.setImageUrl(dishModel.getImageUrl());
        dishEntity.setActive(true);
        dishPersistencePort.saveDish(dishEntity);
    }
}
