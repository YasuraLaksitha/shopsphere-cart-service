package com.shopsphere.cart_service.exceptions;

import com.shopsphere.cart_service.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceAlreadyExistException.class})
    public ResponseEntity<ErrorResponseDTO> handleResourceAlreadyExistException(final RuntimeException ex,
                                                                                final WebRequest request) {
        final ErrorResponseDTO responseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.CONFLICT.name())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
    }
}
