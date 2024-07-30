package com.drow.plazoleta.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDishRequestDto {
    @NotNull(message = "El id del plato no puede ser nulo")
    private Integer id;
    private Float price;
    private String description;
}
