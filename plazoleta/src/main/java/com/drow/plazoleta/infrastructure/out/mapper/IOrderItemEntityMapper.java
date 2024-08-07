package com.drow.plazoleta.infrastructure.out.mapper;

import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.infrastructure.out.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderItemEntityMapper {
    OrderItemEntity toEntity(OrderItemModel orderItemModel);

    @Mapping(target = "order", ignore = true)
    OrderItemModel toModel(OrderItemEntity orderItemEntity);
}
