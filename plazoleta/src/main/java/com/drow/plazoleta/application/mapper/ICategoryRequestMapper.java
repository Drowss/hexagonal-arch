package com.drow.plazoleta.application.mapper;

import com.drow.plazoleta.application.dto.request.CategoryRequestDto;
import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {
    CategoryModel toCategory(CategoryRequestDto categoryRequestDto);
}
