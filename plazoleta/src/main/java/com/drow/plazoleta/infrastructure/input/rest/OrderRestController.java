package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.OrderDishRequestDto;
import com.drow.plazoleta.application.handler.IOrderItemHandler;
import com.drow.plazoleta.application.handler.IOrderHandler;
import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.spi.IOrderItemPersistencePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final IOrderItemHandler orderDishHandler;
    private final IOrderItemPersistencePort orderItemPersistencePort;

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "order created", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<Void> saveOrder(@CookieValue("token") String token, @RequestParam String restaurantNit) {
        orderHandler.saveOrder(token, restaurantNit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Add a new detail to the order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "detail created", content = @Content)
    })
    @PostMapping("/detail")
    public ResponseEntity<Void> saveOrderDetail(@RequestBody @Valid OrderDishRequestDto orderRequestDto) {
        orderDishHandler.saveOrderDishDetail(orderRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
