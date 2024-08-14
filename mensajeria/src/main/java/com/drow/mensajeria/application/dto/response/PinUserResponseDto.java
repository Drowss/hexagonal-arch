package com.drow.mensajeria.application.dto.response;

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
