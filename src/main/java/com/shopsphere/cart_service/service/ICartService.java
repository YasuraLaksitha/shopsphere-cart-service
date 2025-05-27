package com.shopsphere.cart_service.service;

import com.shopsphere.cart_service.dto.CartDTO;
import com.shopsphere.cart_service.dto.CartItemDTO;
import com.shopsphere.cart_service.entity.CartEntity;

public interface ICartService {

    /**
     *
     * @return -cartEntity
     */
    CartEntity createNewCart();

    /**
     *
     * @param productName - product name
     * @param quantity - quantity
     * @return - updated cart
     */
    CartDTO addItemToCart(String productName, Integer quantity);

    /**
     *
     * @param cartItem - product name
     * @return - updated cart
     */
    CartDTO removeItemFromCart(CartItemDTO cartItem);
}
