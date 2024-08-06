package com.drow.plazoleta.infrastructure.out.mapper;

import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.infrastructure.out.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {
    OrderModel toModel(OrderEntity orderEntity);

    OrderEntity toEntity(OrderModel orderModel);
}
