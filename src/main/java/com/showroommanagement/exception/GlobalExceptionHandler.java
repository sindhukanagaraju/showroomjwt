package com.showroommanagement.exception;

import com.showroommanagement.dto.ResponseDTO;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestServiceAlertException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestServiceAlertException(final BadRequestServiceAlertException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseDTO> handleAuthorizationDeniedException(final AuthorizationDeniedException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ResponseDTO> handleUnAuthorizedException(final UnAuthorizedException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseDTO> handleSignatureException(final SignatureException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.FORBIDDEN.value(),exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ResponseDTO> handleExpiredJwtException(final SecurityException exception, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.FORBIDDEN.value(),exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleSecurityException(final Exception exception, WebRequest request) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}