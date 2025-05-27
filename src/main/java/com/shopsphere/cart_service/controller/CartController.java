package com.shopsphere.cart_service.controller;

import com.shopsphere.cart_service.constants.ApplicationDefaultConstaints;
import com.shopsphere.cart_service.dto.CartDTO;
import com.shopsphere.cart_service.dto.ContactInfoDTO;
import com.shopsphere.cart_service.dto.ResponseDTO;
import com.shopsphere.cart_service.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/carts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CartController {

    private final ContactInfoDTO contactInfo;

    private final ICartService cartService;

    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfoDTO> getBuildInfo() {
        return ResponseEntity.ok(contactInfo);
    }

    @PostMapping("/add/products/{productName}")
    public ResponseEntity<CartDTO> addToCart(@PathVariable String productName, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(productName, quantity));
    }
}
