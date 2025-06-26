package com.shopsphere.cart_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "User Cart",
        description = "Schema for hold user cart information"
)
public class CartItemDTO {

    @Schema(description = "Item name representing the product")
    @NotEmpty(message = "Item name is required")
    private String itemName;

    @Schema(description = "Item quantity", example = "2")
    @NotNull(message = "Item quantity is required")
    @Positive(message = "Item quantity should be positive")
    private Integer itemQuantity;

    @Schema(description = "Total item price of one a single product that user favored to purchase")
    @NotNull(message = "Total item price is required")
    @Positive(message = "Total item price should be positive")
    private Double totalItemPrice;

    @Schema(description = "Price of the product")
    @NotNull(message = "Item price is required")
    @Positive(message = "Item price should be positive")
    private Double pricePerUnit;
}
