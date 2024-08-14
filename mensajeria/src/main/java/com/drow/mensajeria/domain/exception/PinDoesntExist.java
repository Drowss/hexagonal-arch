package com.drow.mensajeria.domain.exception;

public class PinDoesntExist extends RuntimeException{
    public PinDoesntExist(String message) {
        super(message);
    }
}
