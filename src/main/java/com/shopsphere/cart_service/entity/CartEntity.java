package com.shopsphere.cart_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.BaseErrorListener;

import java.util.List;

@Data
@Entity
@Table(name = "tbl_cart")
@EntityListeners(value = BaseErrorListener.class)
public class CartEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    private List<Long> cartItemIds;

    private Double totalCartPrice;
}
