package com.drow.plazoleta.infrastructure.out.feign.adapter;

import com.drow.plazoleta.domain.dto.TraceabilityResponseDto;
import com.drow.plazoleta.domain.spi.TraceabilityFeignPort;
import com.drow.plazoleta.infrastructure.out.feign.TraceabilityFeignClientAdapter;
import com.drow.plazoleta.domain.dto.TraceabilityRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements TraceabilityFeignPort {

    private final TraceabilityFeignClientAdapter traceabilityFeignClientAdapter;

    @Override
    public void saveTraceability(TraceabilityResponseDto responseDto) {
        traceabilityFeignClientAdapter.saveTraceability(responseDto);
    }

    @Override
    public TraceabilityResponseDto findTraceabilityByOrderId(Integer orderId) {
        return traceabilityFeignClientAdapter.findTraceabilityByOrderId(orderId);
    }
}
