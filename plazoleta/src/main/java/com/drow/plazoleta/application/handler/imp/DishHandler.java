package com.drow.plazoleta.application.handler.imp;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.handler.IDishHandler;
import com.drow.plazoleta.application.mapper.IDishRequestMapper;
import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.model.DishModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort categoryServicePort;
    private final IDishRequestMapper restaurantRequestMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        DishModel dishModel = restaurantRequestMapper.toDish(dishRequestDto);
        categoryServicePort.saveDish(dishModel);
    }
}
