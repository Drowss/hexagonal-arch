package com.drow.plazoleta.application.exceptionhandler;

import com.drow.plazoleta.application.exception.CategoryDoesntExist;
import com.drow.plazoleta.application.exception.RestaurantDoesntExist;
import com.drow.plazoleta.application.exception.UserNoPermissions;
import com.drow.plazoleta.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(UserNoPermissions.class)
    public ResponseEntity<String> handleUserNoPermissions(UserNoPermissions e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(CategoryDoesntExist.class)
    public ResponseEntity<String> handleCategoryDoesntExist(CategoryDoesntExist e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RestaurantDoesntExist.class)
    public ResponseEntity<String> handleRestaurantDoesntExist(RestaurantDoesntExist e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PendingOrderException.class)
    public ResponseEntity<String> handlePendingOrderException(PendingOrderException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DishNotOwnedRestaurant.class)
    public ResponseEntity<String> handleDishNotOwnedRestaurant(DishNotOwnedRestaurant e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DishNotFound.class)
    public ResponseEntity<String> handleDishNotFound(DishNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RestaurantAlreadyExists.class)
    public ResponseEntity<String> handleRestaurantAlreadyExists(RestaurantAlreadyExists e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NotYourOrder.class)
    public ResponseEntity<String> handleNotYourOrder(NotYourOrder e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NotYourRestaurant.class)
    public ResponseEntity<String> handleNotYourRestaurant(NotYourRestaurant e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
