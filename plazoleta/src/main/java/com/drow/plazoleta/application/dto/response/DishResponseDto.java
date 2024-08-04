package com.drow.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishResponseDto {
    private String name;
    private Float price;
    private Boolean active;
    private String description;
    private String imageUrl;
    private CategoryResponseDto category;
    private RestaurantResponseDto restaurant;
}
