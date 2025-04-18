package com.scaler.userservicecapstone.exceptions;

import com.scaler.userservicecapstone.dtos.ResponseErrorMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<ResponseErrorMessageDto> handleLoginException(UserLoginException ex) {
        return ResponseEntity.badRequest().body(new ResponseErrorMessageDto(ex.getMessage()));
    }
}
