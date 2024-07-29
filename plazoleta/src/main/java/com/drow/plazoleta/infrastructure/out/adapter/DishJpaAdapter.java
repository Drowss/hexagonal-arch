package com.drow.plazoleta.infrastructure.out.adapter;


import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import com.drow.plazoleta.infrastructure.out.mapper.IDishEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        DishEntity dishEntity = dishRepository.save(dishEntityMapper.toEntity(dishModel));
        return dishEntityMapper.toModel(dishEntity);
    }
}
