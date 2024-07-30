package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.DishRequestDto;
import com.drow.plazoleta.application.dto.request.ModifyDishRequestDto;
import com.drow.plazoleta.application.handler.IDishHandler;
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
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<Void> saveDish(@RequestBody @Valid DishRequestDto dishRequestDto) {
        dishHandler.saveDish(dishRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Modify a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Dish modified", content = @Content)
    })
    @PutMapping("/modify")
    public ResponseEntity<Void> modifyDish(@RequestBody @Valid ModifyDishRequestDto modifyDishRequestDto) {
        dishHandler.modifyDish(modifyDishRequestDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
