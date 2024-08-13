package com.drow.user.application.feign;

import com.drow.user.application.feign.dto.RestaurantEmployeeRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "plazoleta", configuration = FeignClientInterceptor.class)
public interface PlazoletaFeignClient {

    @PostMapping("/api/v1/restaurant-employee/save")
    void saveRestaurantEmployee(@RequestBody RestaurantEmployeeRequestDto employeeRequestDto);
}
