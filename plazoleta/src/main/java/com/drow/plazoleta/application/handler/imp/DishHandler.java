package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.dto.request.ModifyDishRequestDto;
import com.drow.plazoleta.application.handler.IDishHandler;
import com.drow.plazoleta.application.mapper.IDishRequestMapper;
import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.ModifyDishModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        DishModel dishModel = dishRequestMapper.toDish(dishRequestDto);
        dishServicePort.saveDish(dishModel);
    }

    @Override
    public void modifyDish(ModifyDishRequestDto modifyDishRequestDto) {
        ModifyDishModel modifyDishModel = dishRequestMapper.toModifyDish(modifyDishRequestDto);
        dishServicePort.modifyDish(modifyDishModel);
    }
}
