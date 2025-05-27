package com.shopsphere.cart_service.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String productName;

    private String productDescription;

    private Integer productQuantity;

    private String productImage;

    private Double productPrice;

    private Double productDiscountPrice;

    private Double productSpecialPrice;
}
