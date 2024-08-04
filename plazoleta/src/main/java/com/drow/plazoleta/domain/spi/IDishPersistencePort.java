package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.application.dto.response.DishResponseDto;
import com.drow.plazoleta.domain.model.DishDomain;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishPersistencePort {
    DishModel saveDish(DishEntity dishEntity);
    DishEntity getDishById(Integer id);

    Page<DishDomain> getDishesRestaurant(String nit, Pageable pageable);
}
