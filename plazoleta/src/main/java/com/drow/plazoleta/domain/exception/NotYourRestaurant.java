package com.drow.plazoleta.domain.exception;

public class NotYourRestaurant extends RuntimeException{
    public NotYourRestaurant(String message) {
        super(message);
    }
}
