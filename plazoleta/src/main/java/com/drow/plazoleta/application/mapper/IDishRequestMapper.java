package com.drow.plazoleta.application.mapper;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.dto.request.ModifyDishRequestDto;
import com.drow.plazoleta.application.dto.response.DishResponseDto;
import com.drow.plazoleta.domain.model.DishDomain;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.ModifyDishModel;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {
    DishModel toDish(DishRequestDto dishRequestDto);

    ModifyDishModel toModifyDish(ModifyDishRequestDto modifyDishRequestDto);

    DishResponseDto toDishResponseDto(DishDomain dishDomain);
}
