package com.sinans.ecommercebackend.Controller.Exceptions;

import com.sinans.ecommercebackend.Controller.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        PageExceptionDTO pageExceptionDTO = PageExceptionDTO.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();
        
        return new ResponseEntity<>(pageExceptionDTO, HttpStatus.NOT_FOUND);
    }

}
