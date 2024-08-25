package com.springboot.base.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.base.dtos.responses.ErrorDTOResponse;
import com.springboot.base.exceptions.DataExistedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTOResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorDTOResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(DataExistedException.class)
    public ResponseEntity<ErrorDTOResponse> handleDataExistedException(DataExistedException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorDTOResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTOResponse> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDTOResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }
}
