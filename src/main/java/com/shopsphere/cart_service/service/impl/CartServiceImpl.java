package com.shopsphere.cart_service.service.impl;

import com.shopsphere.cart_service.constants.ApplicationDefaultConstaints;
import com.shopsphere.cart_service.entity.CartEntity;
import com.shopsphere.cart_service.exceptions.ResourceAlreadyExistException;
import com.shopsphere.cart_service.repository.CartRepository;
import com.shopsphere.cart_service.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;

    @Override
    public void createNewCart() {
        cartRepository.findByCreatedBy("CARTS_MS").ifPresent(existingCart -> {
            throw new ResourceAlreadyExistException("Cart", "username", existingCart.getCreatedBy());
        });

        final CartEntity cartEntity = new CartEntity();
        cartEntity.setTotalCartPrice(ApplicationDefaultConstaints.TOTAL_CART_PRICE);

        cartRepository.save(cartEntity);
    }
}
