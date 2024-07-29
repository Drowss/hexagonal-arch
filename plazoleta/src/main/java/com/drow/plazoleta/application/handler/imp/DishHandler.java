package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.handler.IDishHandler;
import com.drow.plazoleta.application.mapper.IDishRequestMapper;
import com.drow.plazoleta.domain.api.ICategoryServicePort;
import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final ICategoryServicePort categoryServicePort;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        DishModel dishModel = dishRequestMapper.toDish(dishRequestDto);
        CategoryModel categoryModel = categoryServicePort.getCategoryById(dishRequestDto.getCategoryId());
        RestaurantModel restaurantModel = restaurantServicePort.getRestaurantByNit(dishRequestDto.getRestaurantNit());
        dishModel.setCategory(categoryModel);
        dishModel.setRestaurant(restaurantModel);
        dishServicePort.saveDish(dishModel);
    }
}
