package com.shopsphere.cart_service.service;

import com.shopsphere.cart_service.dto.CartDTO;
import com.shopsphere.cart_service.entity.CartEntity;

public interface ICartService {
    
    CartEntity createNewCart();

    CartDTO addItemToCart(String productName, Integer quantity);
}
