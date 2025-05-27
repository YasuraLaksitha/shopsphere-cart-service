package com.shopsphere.cart_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tbl_cart_item")
@EntityListeners(value = EntityListeners.class)
public class CartItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    private Long cartId;

    private String itemName;

    private Integer itemQuantity;

    private Double totalItemPrice;

    private Double pricePerUnit;
}
