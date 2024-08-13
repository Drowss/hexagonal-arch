package com.drow.plazoleta.application.mapper;

import com.drow.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeRequestMapper {
    RestaurantEmployeeModel toModel(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto);
}
