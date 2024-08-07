package com.drow.plazoleta.infrastructure.out.mapper;

import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.infrastructure.out.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderItemEntityMapper {
    OrderItemEntity toEntity(OrderItemModel orderItemModel);

    OrderItemModel toModel(OrderItemEntity orderItemEntity);
}
