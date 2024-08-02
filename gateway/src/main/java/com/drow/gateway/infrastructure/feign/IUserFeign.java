package com.drow.gateway.infrastructure.feign;

import com.drow.gateway.application.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuarios")
public interface IUserFeign {

    @GetMapping("/api/v1/user/getByCorreo")
    UserResponseDto getUserByCorreo(@RequestParam String correo);
}
