package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.CategoryRequestDto;
import com.drow.plazoleta.application.handler.ICategoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<Void> saveCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryHandler.saveCategory(categoryRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
