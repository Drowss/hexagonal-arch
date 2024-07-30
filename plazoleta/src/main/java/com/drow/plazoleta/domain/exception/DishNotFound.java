package com.drow.plazoleta.domain.exception;

public class DishNotFound extends RuntimeException{
    public DishNotFound(String message) {
        super(message);
    }
}
