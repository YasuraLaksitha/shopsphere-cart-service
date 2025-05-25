package com.shopsphere.cart_service.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseDTO {

    private HttpStatus status;

    private String message;

    private LocalDateTime timestamp;
}
