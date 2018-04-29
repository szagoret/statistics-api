package com.szagoret.n26.statistics.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice("com.szagoret.n26.statistics")
public class ExceptionControllerAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorMessage> dataValidationException(WebExchangeBindException exception) {
        String errorMsg = String.format("%s %s", exception.getFieldError().getField(), exception.getFieldError().getDefaultMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(errorMsg));
    }

}
