package com.drow.plazoleta.infrastructure.out.mapper;

import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeEntityMapper {
    RestaurantEmployeeEntity toEntity(RestaurantEmployeeModel restaurantEmployeeModel);
}
