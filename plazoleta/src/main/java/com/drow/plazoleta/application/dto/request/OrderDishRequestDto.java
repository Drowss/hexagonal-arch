package com.drow.plazoleta.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishRequestDto {
    @NotNull
    private Integer orderId;
    @NotNull
    private Integer dishId;
    @NotNull
    private Integer quantity;
}
