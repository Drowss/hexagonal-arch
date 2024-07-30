package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.application.mapper.IDishRequestMapper;
import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.ModifyDishModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.ICategoryPersistencePort;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import com.drow.plazoleta.infrastructure.out.mapper.ICategoryEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IDishEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(DishModel dishModel) {
        CategoryModel categoryModel = categoryPersistencePort.getCategoryById(dishModel.getCategoryId());
        RestaurantModel restaurantModel = restaurantPersistencePort.getRestaurantByNit(dishModel.getRestaurantNit());
        DishEntity dishEntity = dishEntityMapper.toEntity(dishModel);
        dishEntity.setCategory(categoryEntityMapper.toEntity(categoryModel));
        dishEntity.setRestaurant(restaurantEntityMapper.toEntity(restaurantModel));
        dishPersistencePort.saveDish(dishEntity);
    }

    @Override
    public void modifyDish(ModifyDishModel modifyDishModel) {
        DishEntity dishEntity = dishPersistencePort.getDishById(modifyDishModel.getId());
        Optional.ofNullable(modifyDishModel.getPrice()).ifPresent(dishEntity::setPrice);
        Optional.ofNullable(modifyDishModel.getDescription()).ifPresent(dishEntity::setDescription);
        dishPersistencePort.saveDish(dishEntity);
    }
}
