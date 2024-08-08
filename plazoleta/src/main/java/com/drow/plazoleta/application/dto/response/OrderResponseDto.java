package com.drow.plazoleta.application.dto.response;

import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Integer id;
    private RestaurantModel restaurant;
    private Integer userId;

    private OrderStatus status;

    private List<OrderItemResponseDto> items;
}
