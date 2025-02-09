package com.drow.user.infrastructure.input.rest;

import com.drow.user.application.dto.request.EmployeeRequestDto;
import com.drow.user.application.dto.request.UserLoginRequestDto;
import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.dto.response.TokenResponseDto;
import com.drow.user.application.dto.response.UserResponseDto;
import com.drow.user.application.handler.IUserHandler;
import com.drow.user.domain.model.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Create a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created", content = @Content),
            @ApiResponse(responseCode = "406", description = "Owner already exists", content = @Content),
            @ApiResponse(responseCode = "403", description = "Owner underage forbidden", content = @Content)
    })
    @PostMapping("/save/owner")
    public ResponseEntity<Void> saveOwner(@RequestBody @Valid UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Login an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto,
                                                      HttpServletResponse response) {
        return ResponseEntity.ok(userHandler.loginUser(userLoginRequestDto, response));
    }

    @Operation(summary = "Get an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/getByCorreo")
    public ResponseEntity<UserResponseDto> getUserByCorreo(@RequestParam String correo) {
        return ResponseEntity.ok(userHandler.getUserByCorreo(correo));
    }

    @Operation(summary = "Create an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee saved", content = @Content),
            @ApiResponse(responseCode = "406", description = "Employee already exists", content = @Content)
    })
    @PostMapping("/save/employee")
    public ResponseEntity<Void> saveEmployee(@RequestBody @Valid EmployeeRequestDto employeeRequestDto, @CookieValue("token") String token) {
        userHandler.saveEmployee(employeeRequestDto, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Client sign up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client saved", content = @Content),
            @ApiResponse(responseCode = "406", description = "Client already exists", content = @Content)
    })
    @PostMapping("/save/client")
    public ResponseEntity<Void> saveClient(@RequestBody @Valid EmployeeRequestDto employeeRequestDto) {
        userHandler.saveClient(employeeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
