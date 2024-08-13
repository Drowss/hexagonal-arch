package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.dto.request.ModifyDishRequestDto;
import com.drow.plazoleta.application.dto.response.DishResponseDto;
import com.drow.plazoleta.application.handler.IDishHandler;
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
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<Void> saveDish(@RequestBody @Valid DishRequestDto dishRequestDto, @CookieValue String token) {
        dishHandler.saveDish(dishRequestDto, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Modify a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Dish modified", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish not found", content = @Content)
    })
    @PutMapping("/modify")
    public ResponseEntity<Void> modifyDish(@RequestBody @Valid ModifyDishRequestDto modifyDishRequestDto) {
        dishHandler.modifyDish(modifyDishRequestDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Toggle a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish toggled successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish not found", content = @Content)
    })
    @PutMapping("/toggle")
    public ResponseEntity<Void> toggleDish(@RequestParam Integer id, @CookieValue("token") String token) {
        dishHandler.toggleDish(id, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Retrieves all dishes from a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all the dishes from the given restaurant NIT." +
                    " The default page number is 0 and the default page size is 10." +
                    " The categoryId parameter is optional and can be used to filter dishes by category.", content = @Content)
    })
    @GetMapping("/dishes")
    public Page<DishResponseDto> getDishesRestaurant(@RequestParam String nit,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(required = false) Integer categoryId) {
        return dishHandler.getDishesRestaurant(nit, page, size, categoryId);
    }
}
