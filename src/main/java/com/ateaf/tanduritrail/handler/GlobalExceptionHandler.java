package com.ateaf.tanduritrail.handler;

import com.ateaf.tanduritrail.exception.UserAlreadyExistsException;
import com.ateaf.tanduritrail.exception.UserNotFoundException;
import com.ateaf.tanduritrail.exception.UserRoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<String> userRoleNotFoundHandler(UserRoleNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> UserAlreadyExistsHandler(UserAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> UserAlreadyExistsHandler(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> UsernameNotFoundHandler(UsernameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> userNotFoundHandler(MethodArgumentNotValidException exception){
        var errors = new HashMap<String,String>();

        exception.getBindingResult().getAllErrors()
                .forEach( err -> {
                    var fieldName = ((FieldError) err).getField();
                    var errorMessage = err.getDefaultMessage();
                    errors.put(fieldName,errorMessage);
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }
}
