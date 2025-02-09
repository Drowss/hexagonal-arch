package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.OrderDishRequestDto;
import com.drow.plazoleta.application.dto.response.OrderResponseDto;
import com.drow.plazoleta.application.handler.IOrderItemHandler;
import com.drow.plazoleta.application.handler.IOrderHandler;
import com.drow.plazoleta.domain.dto.RestaurantEfficiencyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;
    private final IOrderItemHandler orderDishHandler;

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
            @ApiResponse(responseCode = "201", description = "detail created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Dish not own to this restaurant", content = @Content)
    })
    @PostMapping("/detail")
    public ResponseEntity<Void> saveOrderDetail(@RequestBody @Valid OrderDishRequestDto orderRequestDto) {
        orderDishHandler.saveOrderDishDetail(orderRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all orders by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all orders by status", content = @Content)
    })
    @GetMapping("/all")
    public Page<OrderResponseDto> getAllOrdersByStatus(@CookieValue("token") String token,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "EN_PREPARACION") String status) {
        return orderHandler.findAllByStatus(token, page, size, status);
    }

    @Operation(summary = "Assign an employee to an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee assigned", content = @Content)
    })
    @PutMapping("/assign")
    public Page<OrderResponseDto> assignEmployeeToOrder(@CookieValue("token") String token,
                                                  @RequestParam Integer orderId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "EN_PREPARACION") String status) {
        return orderHandler.assignEmployeeToOrder(token, orderId, page, size, status);
    }

    @Operation(summary = "Toggle to ready an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee assigned", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order taken by another employer", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order status is not preparation", content = @Content)
    })
    @PutMapping("/ready")
    public void readyOrder(@CookieValue("token") String token, @RequestParam Integer orderId) {
        orderHandler.readyOrder(token, orderId);
    }

    @Operation(summary = "Order delivered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status delivered", content = @Content),
            @ApiResponse(responseCode = "409", description = "You cannot mark an order as delivered if it does not belong to your restaurant.", content = @Content),
            @ApiResponse(responseCode = "409", description = "you cannot mark a delivered order if you are not the assigned employee", content = @Content),
            @ApiResponse(responseCode = "409", description = "Pin is incorrect", content = @Content)
    })
    @PutMapping("/deliver")
    public void deliverOrder(@CookieValue("token") String token, @RequestParam Integer orderId, @RequestParam Integer pin) {
        orderHandler.deliverOrder(token, orderId, pin);
    }

    @DeleteMapping("/delete")
    public void deleteOrder(@CookieValue("token") String token, @RequestParam Integer orderId) {
        orderHandler.deleteOrder(token, orderId);
    }

    @GetMapping("/efficiency")
    public RestaurantEfficiencyDto getEfficiency(@CookieValue("token") String token) {
        return orderHandler.getEfficiency(token);
    }
}
