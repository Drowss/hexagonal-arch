package com.drow.plazoleta.infrastructure.out.feign;

import com.drow.plazoleta.domain.dto.TraceabilityRequestDto;
import com.drow.plazoleta.domain.dto.TraceabilityResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "trazabilidad")
public interface TraceabilityFeignClientAdapter {
    @PostMapping("/api/v1/traceability/save")
    void saveTraceability(@RequestBody TraceabilityResponseDto traceabilityResponseDto);

    @GetMapping("/api/v1/traceability/find")
    TraceabilityResponseDto findTraceabilityByOrderId(@RequestParam Integer orderId);
}
