package com.drow.plazoleta.infrastructure.out.feign;

import com.drow.plazoleta.domain.dto.RestaurantEfficiencyDto;
import com.drow.plazoleta.domain.dto.TraceabilityResponseDto;
import com.drow.plazoleta.domain.model.OrderModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "trazabilidad")
public interface TraceabilityFeignClientAdapter {
    @PostMapping("/api/v1/traceability/save")
    void saveTraceability(@RequestBody TraceabilityResponseDto traceabilityResponseDto);

    @GetMapping("/api/v1/traceability/find")
    TraceabilityResponseDto findTraceabilityByOrderId(@RequestParam Integer orderId);

    @DeleteMapping("/api/v1/traceability/delete")
    void deleteTraceabilityByOrderId(@RequestParam String orderId);

    @PostMapping("/api/v1/traceability/restaurant-efficiency")
    RestaurantEfficiencyDto restaurantEfficiency(@RequestBody List<OrderModel> orderModelList);
}
