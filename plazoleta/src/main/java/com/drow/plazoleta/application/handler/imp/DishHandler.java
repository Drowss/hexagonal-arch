package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.dto.request.ModifyDishRequestDto;
import com.drow.plazoleta.application.dto.response.DishResponseDto;
import com.drow.plazoleta.application.handler.IDishHandler;
import com.drow.plazoleta.application.mapper.IDishRequestMapper;
import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.ModifyDishModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto, String token) {
        DishModel dishModel = dishRequestMapper.toDish(dishRequestDto);
        dishServicePort.saveDish(dishModel, token);
    }

    @Override
    public void modifyDish(ModifyDishRequestDto modifyDishRequestDto) {
        ModifyDishModel modifyDishModel = dishRequestMapper.toModifyDish(modifyDishRequestDto);
        dishServicePort.modifyDish(modifyDishModel);
    }

    @Override
    public void toggleDish(Integer id, String token) {
        dishServicePort.toggleDish(id, token);
    }

    @Override
    public Page<DishResponseDto> getDishesRestaurant(String nit, int page, int size, Integer categoryId) {
        return dishServicePort.getDishesRestaurant(nit, page, size, categoryId).map(dishRequestMapper::toDishResponseDto);
    }
}
