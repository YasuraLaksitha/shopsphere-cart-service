package com.shopsphere.cart_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.BaseErrorListener;

@Data
@Entity
@Table(name = "tbl_cart_item")
@EntityListeners(value = BaseErrorListener.class)
public class CartItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    private String itemName;

    private Integer itemQuantitry;

    private Double totalItemPrice;
}
