package com.suporte.demo.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class Exceptions {



    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex){
        ErrorResponse erroResponse = new ErrorResponse(
            ex.getStatusCode().value(),
            ex.getReason() 
            );
    return new ResponseEntity<>(erroResponse, ex.getStatusCode());
    }
    
}
