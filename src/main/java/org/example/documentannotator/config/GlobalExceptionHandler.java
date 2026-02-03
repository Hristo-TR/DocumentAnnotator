package org.example.documentannotator.config;

import org.example.documentannotator.data.exception.DuplicateDocumentException;
import org.example.documentannotator.data.exception.EntityInUseException;
import org.example.documentannotator.data.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Global exception handler for controllers.
     * Used for mapping exceptions to a suitable HTTP status in a single place
     *
     * @param ex thrown exception
     * @return Response entity object with a specific HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if (ex instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            response.put("error", ex.getMessage() != null ? ex.getMessage() : "Entity not found");
        } else if (ex instanceof DuplicateDocumentException) {
            status = HttpStatus.CONFLICT;
            response.put("error", ex.getMessage() != null ? ex.getMessage() : "Document with this name already exists");
        } else if (ex instanceof EntityInUseException) {
            status = HttpStatus.CONFLICT;
            response.put("error", ex.getMessage());
        } else if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.BAD_REQUEST;
            Map<String, String> fieldErrors = new HashMap<>();
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                fieldErrors.put(fieldName, errorMessage);
            });
            response.put("error", "Validation failed");
            response.put("fieldErrors", fieldErrors);
        } else if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            response.put("error", ex.getMessage());
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.put("error", ex.getMessage() != null ? ex.getMessage() : "Internal server error");
        }

        return ResponseEntity.status(status).body(response);
    }
}
