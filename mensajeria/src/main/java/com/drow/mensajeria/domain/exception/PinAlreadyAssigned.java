package com.drow.mensajeria.domain.exception;

public class PinAlreadyAssigned extends RuntimeException {
    public PinAlreadyAssigned(String message) {
        super(message);
    }
}
