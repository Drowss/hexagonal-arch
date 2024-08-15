package com.drow.trazabilidad.infrastructure.input.rest;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.application.dto.response.TraceabilityResponseDto;
import com.drow.trazabilidad.application.handler.ITraceabilityHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/traceability")
@RequiredArgsConstructor
public class TraceabilityRestController {

    private final ITraceabilityHandler traceabilityHandler;

    @PostMapping("/save")
    public void saveTraceability(@RequestBody TraceabilityRequestDto traceabilityRequestDto) {
        System.out.println(traceabilityRequestDto.getId());
        traceabilityHandler.saveTraceability(traceabilityRequestDto);
    }

    @GetMapping("/find")
    public TraceabilityResponseDto findTraceabilityByOrderId(@RequestParam Integer orderId) {
        return traceabilityHandler.findTraceabilityByOrderId(orderId);
    }

}
