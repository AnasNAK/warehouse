package com.progressoft.warehouse.exceptions;

import com.progressoft.warehouse.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return new ErrorResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value(),
                null
        );
    }

    @ExceptionHandler(DuplicateRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateRequest(DuplicateRequestException ex, WebRequest request) {
        return new ErrorResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT.value(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllExceptions(Exception ex, WebRequest request) {
        return new ErrorResponse(
                Instant.now(),
                "Internal Server Error",
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
    }

}
