package com.drow.user.infrastructure.input.rest;

import com.drow.user.application.dto.request.UserLoginRequestDto;
import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "user created", content = @Content),
            @ApiResponse(responseCode = "409", description = "user already exists", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<Void> saveObject(@RequestBody @Valid UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Login a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user logged in", content = @Content),
            @ApiResponse(responseCode = "401", description = "user not found", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<Void> loginObject(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto,
                                            HttpServletResponse response) {
        userHandler.loginUser(userLoginRequestDto, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
