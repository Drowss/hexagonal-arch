package com.drow.plazoleta.application.exception;

public class RestaurantDoesntExist extends RuntimeException {
    public RestaurantDoesntExist(String message) {
        super(message);
    }
}
