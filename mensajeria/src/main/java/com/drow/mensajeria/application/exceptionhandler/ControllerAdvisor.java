package com.drow.mensajeria.application.exceptionhandler;

import com.drow.mensajeria.domain.exception.PinAlreadyAssigned;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(PinAlreadyAssigned.class)
    public ResponseEntity<String> handlePinAlreadyAssigned(PinAlreadyAssigned e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
