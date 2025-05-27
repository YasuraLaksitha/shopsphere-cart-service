package com.shopsphere.cart_service.controller;

import com.shopsphere.cart_service.dto.CartDTO;
import com.shopsphere.cart_service.dto.CartItemDTO;
import com.shopsphere.cart_service.dto.ContactInfoDTO;
import com.shopsphere.cart_service.service.ICartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/carts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CartController {

    private final ContactInfoDTO contactInfo;

    private final ICartService cartService;

    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfoDTO> getBuildInfo() {
        return ResponseEntity.ok(contactInfo);
    }

    @PostMapping("/user/add/products/{productName}")
    public ResponseEntity<CartDTO> addToCart(
            @NotEmpty(message = "product name is required") @PathVariable String productName,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(productName, quantity));
    }

    @PostMapping("/user/remove/item")
    public ResponseEntity<CartDTO> removeFromCart(@Valid @RequestBody final CartItemDTO cartItem) {
        return ResponseEntity.ok(cartService.removeItemFromCart(cartItem));
    }
}
