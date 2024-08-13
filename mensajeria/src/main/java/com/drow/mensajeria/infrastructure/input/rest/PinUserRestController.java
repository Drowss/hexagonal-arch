package com.drow.mensajeria.infrastructure.input.rest;

import com.drow.mensajeria.application.handler.IPinUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pin")
@RequiredArgsConstructor
public class PinUserRestController {

    private final IPinUserHandler pinUserHandler;

    @PostMapping("/save")
    public void savePinUser(@RequestParam Integer userId,@RequestParam Integer orderId) {
        pinUserHandler.savePinUser(userId, orderId);
    }

}
