package com.shopsphere.cart_service.controller;

import com.shopsphere.cart_service.constants.ApplicationDefaultConstaints;
import com.shopsphere.cart_service.dto.ContactInfoDTO;
import com.shopsphere.cart_service.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/carts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CartController {

    private final ContactInfoDTO contactInfo;

    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfoDTO> getBuildInfo() {
        return ResponseEntity.ok(contactInfo);
    }
}
