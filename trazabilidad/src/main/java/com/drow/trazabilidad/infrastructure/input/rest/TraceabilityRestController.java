package com.drow.trazabilidad.infrastructure.input.rest;

import com.drow.trazabilidad.application.dto.request.TraceabilityRequestDto;
import com.drow.trazabilidad.application.dto.response.TraceabilityResponseDto;
import com.drow.trazabilidad.application.handler.ITraceabilityHandler;
import com.drow.trazabilidad.domain.dto.OrderModelDto;
import com.drow.trazabilidad.domain.dto.response.RestaurantEfficiencyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/delete")
    public void deleteTraceabilityByOrderId(@RequestParam String orderId) {
        traceabilityHandler.deleteTraceabilityByOrderId(orderId);
    }

    @PostMapping("/restaurant-efficiency")
    public RestaurantEfficiencyDto getEfficiency(@RequestBody List<OrderModelDto> orderModelList) {
        return traceabilityHandler.getEfficiency(orderModelList);
    }

}
