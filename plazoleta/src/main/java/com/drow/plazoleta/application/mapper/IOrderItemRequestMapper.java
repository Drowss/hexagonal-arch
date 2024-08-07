package com.drow.plazoleta.application.mapper;

import com.drow.plazoleta.application.dto.request.OrderDishRequestDto;
import com.drow.plazoleta.domain.model.OrderItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderItemRequestMapper {
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "dishId", target = "dish.id")
    @Mapping(source = "quantity", target = "quantity")
    OrderItemModel toOrderDish(OrderDishRequestDto orderRequestDto);
}
