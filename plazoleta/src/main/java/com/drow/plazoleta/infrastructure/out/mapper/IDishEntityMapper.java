package com.drow.plazoleta.infrastructure.out.mapper;

import com.drow.plazoleta.domain.model.DishDomain;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {
    DishEntity toEntity(DishModel dishModel);
    DishModel toModel(DishEntity dishEntity);

    DishDomain toDomain(DishEntity dishEntity);
}
