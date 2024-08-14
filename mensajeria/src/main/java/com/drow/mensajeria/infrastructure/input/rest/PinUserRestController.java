package com.drow.mensajeria.infrastructure.input.rest;

import com.drow.mensajeria.application.dto.response.PinUserResponseDto;
import com.drow.mensajeria.application.handler.IPinUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pin")
@RequiredArgsConstructor
public class PinUserRestController {

    private final IPinUserHandler pinUserHandler;

    @PostMapping("/save")
    public void savePinUser(@RequestParam Integer userId,@RequestParam Integer orderId) {
        pinUserHandler.savePinUser(userId, orderId);
    }

    @GetMapping("/find")
    public PinUserResponseDto findPinUser(@RequestParam Integer pin) {
        return pinUserHandler.findPinUser(pin);
    }

}
