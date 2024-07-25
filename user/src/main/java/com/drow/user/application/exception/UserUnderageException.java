package com.drow.user.application.exception;

public class UserUnderageException extends RuntimeException {
    public UserUnderageException(String message) {
        super(message);
    }
}
