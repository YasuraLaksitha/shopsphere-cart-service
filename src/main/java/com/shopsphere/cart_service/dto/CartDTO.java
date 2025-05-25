package com.shopsphere.cart_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDTO {

    private List<CartItemDTO> cartItems;

    private Double totalCartPrice;
}
