package com.drow.plazoleta.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PinUserResponseDto {
    private Integer id;
    private Integer pin;
    private Integer userId;
    private Integer orderId;
}
