package com.showcase.backend.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.showcase.backend.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {

    ErrorResponse errorResponse = new ErrorResponse(
        NOT_FOUND.value(),
        "NOT_FOUND",
        ex.getMessage());

    return ResponseEntity.status(NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

    ErrorResponse errorResponse = new ErrorResponse(
        BAD_REQUEST.value(),
        "BAD_REQUEST",
        ex.getMessage());

    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }
}