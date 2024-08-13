package com.drow.user.application.feign.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEmployeeRequestDto {
    @NotNull
    @NotBlank
    private String restaurantNit;
    @NotNull
    private Integer employeeId;
}
