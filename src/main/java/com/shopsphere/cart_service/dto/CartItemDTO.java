package com.shopsphere.cart_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {

    private String itemName;

    private Integer itemQuantity;

    private Double totalItemPrice;
}
