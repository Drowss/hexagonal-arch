package com.drow.plazoleta.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraceabilityResponseDto {
    private String id;
    private Integer orderId;
    private Integer customerId;
    private String customerEmail;
    private String currentStatus;
    private Integer employeeId;
    private String employeeEmail;
    private LocalDateTime orderStartDate;
    private LocalDateTime orderEndDate;
}
