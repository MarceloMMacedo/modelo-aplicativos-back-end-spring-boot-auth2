package br.com.areadigital.areadigital.resource.exceptions;

import br.com.areadigital.areadigital.service.exceptions.DatabaseException;
import br.com.areadigital.areadigital.service.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * Controller advice class that handles exceptions and returns standard error responses.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * Handles generic exceptions and returns an UNPROCESSABLE_ENTITY response.
     *
     * @param e       Exception that was thrown
     * @param request HTTP request that was made
     * @return ResponseEntity with a StandardError body
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> validation(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Internal processing failure");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        try {
            err.addError("message", e.getCause().getMessage());
        } catch (Exception exception) {

        }

        return ResponseEntity.status(status).body(err);
    }

    /**
     * Handles HTTP request method not supported exceptions and returns a METHOD_NOT_ALLOWED response.
     *
     * @param e       HttpRequestMethodNotSupportedException that was thrown
     * @param request HTTP request that was made
     * @return ResponseEntity with a StandardError body
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> entityMethodNotAllowedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Method Not Allowed");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Handles ResourceNotFoundExceptions thrown by Spring Data REST and returns a NOT_FOUND response.
     *
     * @param e       ResourceNotFoundException that was thrown
     * @param request HTTP request that was made
     * @return ResponseEntity with a StandardError body
     */
    @ExceptionHandler(org.springframework.data.rest.webmvc.ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Resource Not Found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Handles ResourceNotFoundExceptions and returns a NOT_FOUND response.
     *
     * @param e       ResourceNotFoundException that was thrown
     * @param request HTTP request that was made
     * @return ResponseEntity with a StandardError body
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Resource not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Handles DatabaseExceptions and returns a BAD_REQUEST response.
     *
     * @param e       DatabaseException that was thrown
     * @param request HTTP request that was made
     * @return ResponseEntity with a StandardError body
     */
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Database exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }
}
