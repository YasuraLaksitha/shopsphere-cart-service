package com.shopsphere.cart_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(
        name = "User Cart",
        description = "Schema for hold user cart information"
)
public class CartDTO {

    @Schema(description = "cart items representing user favoured products")
    private List<CartItemDTO> cartItems;

    @Schema(description = "total cart price in USD", example = "150.00")
    private BigDecimal totalCartPrice;
}
