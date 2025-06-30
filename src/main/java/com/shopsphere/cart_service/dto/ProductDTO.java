package com.shopsphere.cart_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String productName;

    private String productDescription;

    private Integer productQuantity;

    private String productImage;

    private BigDecimal productPrice;

    private BigDecimal productDiscountPrice;

    private BigDecimal productSpecialPrice;
}
