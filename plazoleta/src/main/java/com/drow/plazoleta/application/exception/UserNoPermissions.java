package com.drow.plazoleta.application.exception;

public class UserNoPermissions extends RuntimeException {
    public UserNoPermissions(String message) {
        super(message);
    }
}
