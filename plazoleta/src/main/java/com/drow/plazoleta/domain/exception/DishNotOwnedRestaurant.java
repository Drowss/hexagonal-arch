package com.drow.plazoleta.domain.exception;

public class DishNotOwnedRestaurant extends RuntimeException{
    public DishNotOwnedRestaurant(String message) {
        super(message);
    }
}
