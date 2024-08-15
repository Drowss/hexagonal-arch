package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.dto.TraceabilityResponseDto;

public interface TraceabilityFeignPort {
    void saveTraceability(TraceabilityResponseDto responseDto);

    TraceabilityResponseDto findTraceabilityByOrderId(Integer orderId);
}
