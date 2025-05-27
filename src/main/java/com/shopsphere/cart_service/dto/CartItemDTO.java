package com.shopsphere.cart_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {

    @NotEmpty(message = "Item name is required")
    private String itemName;

    @NotNull(message = "Item quantity is required")
    @Positive(message = "Item quantity should be positive")
    private Integer itemQuantity;

    @NotNull(message = "Item price is required")
    @Positive(message = "Item price should be positive")
    private Double totalItemPrice;
}
