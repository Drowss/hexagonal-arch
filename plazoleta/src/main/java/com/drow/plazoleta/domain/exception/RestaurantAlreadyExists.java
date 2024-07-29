package com.drow.plazoleta.domain.exception;

public class RestaurantAlreadyExists extends RuntimeException {
    public RestaurantAlreadyExists(String message) {
        super(message);
    }
}
