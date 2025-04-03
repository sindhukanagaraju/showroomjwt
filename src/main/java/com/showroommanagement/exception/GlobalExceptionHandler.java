package com.showroommanagement.exception;

import com.showroommanagement.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestServiceAlertException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestServiceAlertException(final BadRequestServiceAlertException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ResponseDTO> handleUserExistsException(final UserExistsException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ResponseDTO> handleUserNotExistException(final UserNotExistException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<String> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleSecurityException(final Exception exception, WebRequest request) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}