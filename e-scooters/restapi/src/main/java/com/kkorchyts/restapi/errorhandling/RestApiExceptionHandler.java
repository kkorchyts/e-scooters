package com.kkorchyts.restapi.errorhandling;

import com.mysql.cj.exceptions.WrongArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(EntityNotFoundException e, WebRequest request) {
        RestApiErrorStatus restApiErrorStatus = new RestApiErrorStatus(HttpStatus.NOT_FOUND, "Entity not found exception!", e.getMessage());
        return new ResponseEntity<>(restApiErrorStatus, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e, WebRequest request) {
        if (e.getCause() instanceof BadCredentialsException) {
            RestApiErrorStatus restApiErrorStatus = new RestApiErrorStatus(HttpStatus.UNAUTHORIZED, "Unauthorized exception!", e.getMessage());
            return new ResponseEntity<>(restApiErrorStatus, HttpStatus.UNAUTHORIZED);
        } else {
            return handleAllExceptions(e, request);
        }
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
        RestApiErrorStatus restApiErrorStatus = new RestApiErrorStatus(HttpStatus.INTERNAL_SERVER_ERROR, "Server exception!", e.getMessage());
        return new ResponseEntity<>(restApiErrorStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessExceptions(Exception e, WebRequest request) {
        RestApiErrorStatus restApiErrorStatus = new RestApiErrorStatus(HttpStatus.FORBIDDEN, "Access denied exception!", e.getMessage());
        return new ResponseEntity<>(restApiErrorStatus, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(WrongArgumentException.class)
    protected ResponseEntity<Object> handleWrongArgumentException(WrongArgumentException e, WebRequest request) {
        RestApiErrorStatus restApiErrorStatus = new RestApiErrorStatus(HttpStatus.BAD_REQUEST, "Wrong argument exception!", e.getMessage());
        return new ResponseEntity<>(restApiErrorStatus, HttpStatus.BAD_REQUEST);
    }


}
