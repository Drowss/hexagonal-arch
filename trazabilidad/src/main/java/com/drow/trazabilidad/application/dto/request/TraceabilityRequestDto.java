package com.drow.trazabilidad.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraceabilityRequestDto {
    private String id;
    private Integer orderId;
    private Integer customerId;
    private String customerEmail;
    private String currentStatus;
    private Integer employeeId;
    private String employeeEmail;
    private LocalDate orderStartDate;
    private LocalDate orderEndDate;
}
