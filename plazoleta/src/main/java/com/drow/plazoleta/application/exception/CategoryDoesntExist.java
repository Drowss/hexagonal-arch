package com.drow.plazoleta.application.exception;

public class CategoryDoesntExist extends RuntimeException {
    public CategoryDoesntExist(String message) {
        super(message);
    }
}
