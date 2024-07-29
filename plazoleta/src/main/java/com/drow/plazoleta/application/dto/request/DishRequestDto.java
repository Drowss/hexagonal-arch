package com.drow.plazoleta.application.dto.request;

import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishRequestDto {
    @NotNull(message = "El nombre del plato no puede ser nulo")
    @NotEmpty(message = "El nombre del plato no puede estar vacío")
    private String name;

    @NotNull(message = "El precio del plato no puede ser nulo")
    @Positive(message = "El precio del plato debe ser un número entero positivo mayor a 0")
    private Float price;

    private Boolean active = true;

    @NotNull(message = "La descripción no puede ser nula")
    @NotEmpty(message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(message = "La URL de la imagen no puede ser nula")
    @NotEmpty(message = "La URL de la imagen no puede estar vacía")
    private String imageUrl;

    @NotNull(message = "La categoría no puede ser nula")
    private CategoryModel category;

    @NotNull(message = "El restaurante no puede ser nulo")
    private RestaurantModel restaurant;
}
