package com.drow.plazoleta.infrastructure.out.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mensajeria")
public interface PinUserFeignClientAdapter {

    @PostMapping("/api/v1/pin/save")
    void savePinUser(@RequestParam Integer userId,@RequestParam Integer orderId);
}
