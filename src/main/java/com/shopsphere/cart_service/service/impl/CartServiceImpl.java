package com.shopsphere.cart_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.cart_service.constants.ApplicationDefaultConstaints;
import com.shopsphere.cart_service.dto.CartDTO;
import com.shopsphere.cart_service.dto.CartItemDTO;
import com.shopsphere.cart_service.dto.ProductDTO;
import com.shopsphere.cart_service.entity.CartEntity;
import com.shopsphere.cart_service.entity.CartItemEntity;
import com.shopsphere.cart_service.exceptions.ResourceAlreadyExistException;
import com.shopsphere.cart_service.exceptions.ResourceNotFoundException;
import com.shopsphere.cart_service.repository.CartItemRepository;
import com.shopsphere.cart_service.repository.CartRepository;
import com.shopsphere.cart_service.service.ICartService;
import com.shopsphere.cart_service.service.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ObjectMapper objectMapper;

    private final ProductClient productClient;

    @Override
    public CartEntity createNewCart() {
        cartRepository.findByCreatedBy("CARTS_MS").ifPresent(existingCart -> {
            throw new ResourceAlreadyExistException("Cart", "username", existingCart.getCreatedBy());
        });

        final CartEntity cartEntity = new CartEntity();
        cartEntity.setCartItemIds(new ArrayList<>());
        cartEntity.setTotalCartPrice(ApplicationDefaultConstaints.TOTAL_CART_PRICE);

        return cartRepository.save(cartEntity);
    }

    @Override
    public CartDTO addItemToCart(final String productName, final Integer quantity) {
        final CartEntity cartEntity = cartRepository.findByCreatedBy("CARTS_MS")
                .orElseGet(this::createNewCart);

        cartItemRepository.findByItemNameEqualsIgnoreCaseAndCartId(productName, cartEntity.getCartId()).ifPresent(existingCartItem -> {
            throw new ResourceAlreadyExistException("CartItem", "item name", existingCartItem.getItemName());
        });

        final ProductDTO product = productClient.getByName(productName).getBody();

        if (Objects.requireNonNull(product).getProductQuantity() - quantity < ApplicationDefaultConstaints.MINIMUM_PRODUCT_THRESHOLD_COUNT)
            throw new RuntimeException("Insufficient stock for this product");

        final CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setItemName(product.getProductName());
        cartItemEntity.setItemQuantity(quantity);
        cartItemEntity.setTotalItemPrice(product.getProductPrice() * quantity);
        cartItemEntity.setCartId(cartEntity.getCartId());

        final CartItemEntity itemEntity = cartItemRepository.save(cartItemEntity);
        cartEntity.setTotalCartPrice(cartEntity.getTotalCartPrice() + itemEntity.getTotalItemPrice());
        cartEntity.getCartItemIds().add(itemEntity.getCartItemId());
        final CartEntity updatedCart = cartRepository.save(cartEntity);

        final List<CartItemDTO> cartItemDTOList = updatedCart.getCartItemIds().stream()
                .map(cartItemId -> objectMapper.convertValue(cartItemRepository.findById(cartItemId), CartItemDTO.class)
                ).toList();

        return CartDTO.builder()
                .cartItems(cartItemDTOList)
                .totalCartPrice(updatedCart.getTotalCartPrice())
                .build();
    }

    @Override
    public CartDTO removeItemFromCart(CartItemDTO cartItem) {
        final CartEntity cartEntity = cartRepository.findByCreatedBy("CARTS_MS").orElseThrow(
                () -> new ResourceNotFoundException("Cart", "username", "CARTS_MS")
        );
        final CartItemEntity cartItemEntity = cartItemRepository
                .findByItemNameEqualsIgnoreCaseAndCartId(cartItem.getItemName(), cartEntity.getCartId()).orElseThrow(
                () -> new ResourceNotFoundException("CartItem", "item name", cartItem.getItemName())
        );

        cartEntity.setTotalCartPrice(cartEntity.getTotalCartPrice() - cartItemEntity.getTotalItemPrice());
        cartEntity.getCartItemIds().remove(cartItemEntity.getCartItemId());
        cartItemRepository.delete(cartItemEntity);

        final CartEntity updatedCart = cartRepository.save(cartEntity);

        final List<CartItemDTO> cartItemDTOList = updatedCart.getCartItemIds().stream()
                .map(cartItemId -> objectMapper.convertValue(cartItemRepository.findById(cartItemId), CartItemDTO.class)
                ).toList();

        return CartDTO.builder()
                .cartItems(cartItemDTOList)
                .totalCartPrice(updatedCart.getTotalCartPrice())
                .build();
    }
}
