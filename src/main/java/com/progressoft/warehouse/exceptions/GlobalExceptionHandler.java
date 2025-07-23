package com.progressoft.warehouse.exceptions;

import com.progressoft.warehouse.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        log.error("=== RESOURCE NOT FOUND EXCEPTION ===");
        log.error("Exception message: {}", ex.getMessage());

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
        log.error("=== DUPLICATE REQUEST EXCEPTION ===");
        log.error("Exception message: {}", ex.getMessage());

        return new ErrorResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT.value(),
                null
        );
    }

    @ExceptionHandler(RuleViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuleViolation(RuleViolationException ex, WebRequest request) {
        log.error("=== RULE VIOLATION EXCEPTION ===");
        log.error("Exception message: {}", ex.getMessage());

        return new ErrorResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("=== VALIDATION EXCEPTION CAUGHT ===");
        log.error("Exception type: {}", ex.getClass().getName());
        log.error("Exception message: {}", ex.getMessage());

        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
            log.error("Validation error - Field: {}, Message: {}", error.getField(), error.getDefaultMessage());
        }

        return new ErrorResponse(
                Instant.now(),
                "Validation Failed",
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value(),
                validationErrors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        log.error("=== HTTP MESSAGE NOT READABLE EXCEPTION ===");
        log.error("Exception type: {}", ex.getClass().getName());
        log.error("Exception message: {}", ex.getMessage());
        log.error("Root cause: ", ex.getRootCause());

        String message = "Invalid JSON format or data type";
        if (ex.getMessage().contains("JSON parse error")) {
            message = "Invalid JSON syntax";
        } else if (ex.getMessage().contains("Cannot deserialize")) {
            message = "Invalid data format for one or more fields";
        }

        return new ErrorResponse(
                Instant.now(),
                message,
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("=== METHOD ARGUMENT TYPE MISMATCH EXCEPTION ===");
        log.error("Exception type: {}", ex.getClass().getName());
        log.error("Exception message: {}", ex.getMessage());

        String message = String.format("Invalid value for parameter '%s'. Expected type: %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");

        return new ErrorResponse(
                Instant.now(),
                message,
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        log.error("=== ILLEGAL ARGUMENT EXCEPTION ===");
        log.error("Exception type: {}", ex.getClass().getName());
        log.error("Exception message: {}", ex.getMessage());
        log.error("Full stack trace: ", ex);

        return new ErrorResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllExceptions(Exception ex, WebRequest request) {
        log.error("=== GENERIC EXCEPTION CAUGHT ===");
        log.error("Exception type: {}", ex.getClass().getName());
        log.error("Exception message: {}", ex.getMessage());
        log.error("Full stack trace: ", ex);

        return new ErrorResponse(
                Instant.now(),
                "Internal Server Error",
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
    }
}