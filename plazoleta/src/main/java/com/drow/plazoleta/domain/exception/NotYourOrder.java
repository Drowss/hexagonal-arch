package com.drow.plazoleta.domain.exception;

public class NotYourOrder extends RuntimeException{
    public NotYourOrder(String message) {
        super(message);
    }
}
