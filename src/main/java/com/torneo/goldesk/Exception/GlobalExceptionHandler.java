package com.torneo.goldesk.Exception;

import com.torneo.goldesk.dto.errorManage.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GoldeskException.class)
    public ResponseEntity<ErrorResponseDTO> handleGoldeskException(GoldeskException ex,
                                                                   HttpServletRequest request){
        ErrorResponseDTO error= new ErrorResponseDTO(
                ex.getStatus().value(),
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error,ex.getStatus());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericRuntime(RuntimeException ex,
                                                                 HttpServletRequest request){
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "GENERIC_ERROR",
                ex.getMessage(),
                request.getRequestURI(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    // Añade esto a tu GlobalExceptionHandler.java
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN_ACCESS",
                "No tienes permisos suficientes para realizar esta acción",
                request.getRequestURI(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
