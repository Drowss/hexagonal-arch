package com.drow.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Integer id;

    private DishResponseDto dish;

    private Integer quantity;
}
