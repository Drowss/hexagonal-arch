package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.OrderRequestDto;
import com.drow.plazoleta.application.handler.IOrderHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @PostMapping("/save")
    public ResponseEntity<Void> saveObject(@CookieValue("token") String token, @RequestParam String restaurantNit) {
        orderHandler.saveOrder(token, restaurantNit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
