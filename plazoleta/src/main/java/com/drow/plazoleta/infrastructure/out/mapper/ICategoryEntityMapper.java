package com.drow.plazoleta.infrastructure.out.mapper;

import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.infrastructure.out.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {
    CategoryEntity toEntity(CategoryModel categoryModel);
    CategoryModel toModel(CategoryEntity categoryEntity);
}
