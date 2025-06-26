package com.shopsphere.cart_service.controller;

import com.shopsphere.cart_service.dto.*;
import com.shopsphere.cart_service.service.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "User Cart Controller",
        description = "CRUD REST APIs for user cart operations"
)

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CartController {

    private final ContactInfoDTO contactInfo;

    private final ICartService cartService;

    @Operation(
            summary = "Contact information",
            description = "REST API Endpoint for fetch contact information"
    )
    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfoDTO> getBuildInfo() {
        return ResponseEntity.ok(contactInfo);
    }

    @Operation(
            summary = "Add product to cart",
            description = "API Endpoint for add products to cart"
    )
    @ApiResponse(
            responseCode = "409",
            description = "HTTP Status Code CONFLICT",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "HTTP Status Code NOT_FOUND",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Code BAD_REQUEST",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @PostMapping("/user/add/products/{productName}")
    public ResponseEntity<CartDTO> addToCart(
            @NotEmpty(message = "product name is required") @PathVariable String productName,
            @NotNull(message = "Item name is required") @PositiveOrZero(message = "Quantity should be positive or zero")
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(productName, quantity));
    }

    @Operation(
            summary = "Remove product from cart",
            description = "API Endpoint for remove product from cart"
    )
    @ApiResponse(
            responseCode = "404",
            description = "HTTP Status Code NOT_FOUND",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Code BAD_REQUEST",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @DeleteMapping("/user/remove/item")
    public ResponseEntity<CartDTO> removeFromCart(@Valid @RequestBody final CartItemDTO cartItem) {
        return ResponseEntity.ok(cartService.removeItemFromCart(cartItem));
    }

    @Operation(
            summary = "Update item quantity",
            description = "API Endpoint to update existing cart item quantity"
    )
    @ApiResponse(
            responseCode = "404",
            description = "HTTP Status Code NOT_FOUND",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Code BAD_REQUEST",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @PatchMapping("/user/update/item/{itemName}")
    public ResponseEntity<CartDTO> updateItemQuantity(
            @NotEmpty(message = "Item name is required") @PathVariable final String itemName,
            @NotNull(message = "Item name is required") @PositiveOrZero(message = "Quantity should be positive or zero")
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(itemName, quantity));
    }
}
