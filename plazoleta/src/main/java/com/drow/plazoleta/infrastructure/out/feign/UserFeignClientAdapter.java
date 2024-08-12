package com.drow.plazoleta.infrastructure.out.feign;

import com.drow.plazoleta.application.dto.response.UserResponseDto;
import com.drow.plazoleta.infrastructure.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuarios", configuration = FeignConfig.class)
public interface UserFeignClientAdapter {
    @GetMapping("/api/v1/user/getByCorreo")
    ResponseEntity<UserResponseDto> getUserByCorreo(@RequestParam String correo);

}
