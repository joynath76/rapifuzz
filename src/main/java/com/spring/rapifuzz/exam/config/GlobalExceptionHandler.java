package com.spring.rapifuzz.exam.config;

import com.spring.rapifuzz.exam.exception.BadRequestException;
import com.spring.rapifuzz.exam.exception.DuplicateRequestException;
import com.spring.rapifuzz.exam.exception.ResourceNotFoundException;
import com.spring.rapifuzz.exam.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Resource Not Found Exceptions
    @ExceptionHandler({ResourceNotFoundException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(getResponseBody(ex, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethoNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return new ResponseEntity<>(getResponseBody(ex, request), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Handle IllegalArgumentException or Custom Validation Errors
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class, BadRequestException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(getResponseBody(ex, request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(getResponseBody(ex, request), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<Object> handleDuplicateException(DuplicateRequestException ex, WebRequest request) {
        return new ResponseEntity<>(getResponseBody(ex, request), HttpStatus.CONFLICT);
    }

    // Handle All Other Exceptions (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "An unexpected error occurred.");
        response.put("path", request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> getResponseBody(Exception ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", ex.getMessage());
        response.put("path", request.getDescription(false));
        return response;
    }
}

