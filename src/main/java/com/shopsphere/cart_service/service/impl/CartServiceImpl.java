package com.shopsphere.cart_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.cart_service.constants.ApplicationDefaultConstaints;
import com.shopsphere.cart_service.dto.CartDTO;
import com.shopsphere.cart_service.dto.CartItemDTO;
import com.shopsphere.cart_service.dto.ProductDTO;
import com.shopsphere.cart_service.entity.CartEntity;
import com.shopsphere.cart_service.entity.CartItemEntity;
import com.shopsphere.cart_service.exceptions.InsufficientResourcesException;
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

        final Boolean isAvailable = productClient.checkProductAvailability(productName, quantity).getBody();
        if (Boolean.FALSE.equals(isAvailable))
            throw new InsufficientResourcesException("Product", "product name", productName);

        final ProductDTO product = productClient.getByName(productName).getBody();

        final CartItemEntity cartItemEntity = new CartItemEntity();
        assert product != null;
        cartItemEntity.setItemName(product.getProductName());
        cartItemEntity.setPricePerUnit(product.getProductPrice());
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
        final CartEntity cartEntity = retrieveUserCart();
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

    @Override
    public CartDTO updateCartItemQuantity(final String itemName, final Integer quantity) {
        final CartEntity cartEntity = retrieveUserCart();
        final CartItemEntity cartItemEntity = cartItemRepository
                .findByItemNameEqualsIgnoreCaseAndCartId(itemName, cartEntity.getCartId()).orElseThrow(
                        () -> new ResourceNotFoundException("CartItem", "item name", itemName)
                );

        if (quantity == 0)
            return this.removeItemFromCart(objectMapper.convertValue(cartItemEntity, CartItemDTO.class));

        if (Boolean.FALSE.equals(productClient.checkProductAvailability(itemName, quantity).getBody()))
            throw new InsufficientResourcesException("Product", "product name", itemName);

        cartItemEntity.setItemQuantity(quantity);
        final Double oldTotalItemPrice = cartItemEntity.getTotalItemPrice();
        cartItemEntity.setTotalItemPrice(cartItemEntity.getPricePerUnit() * quantity);

        final CartItemEntity itemEntity = cartItemRepository.save(cartItemEntity);

        cartEntity.setTotalCartPrice(cartEntity.getTotalCartPrice() - oldTotalItemPrice + itemEntity.getTotalItemPrice());
        final CartEntity updatedCart = cartRepository.save(cartEntity);

        final List<CartItemDTO> cartItemDTOList = updatedCart.getCartItemIds().stream()
                .map(cartItemId -> objectMapper.convertValue(cartItemRepository.findById(cartItemId), CartItemDTO.class)
                ).toList();

        return CartDTO.builder()
                .cartItems(cartItemDTOList)
                .totalCartPrice(updatedCart.getTotalCartPrice())
                .build();

    }

    private CartEntity retrieveUserCart() {
        return cartRepository.findByCreatedBy("CARTS_MS").orElseThrow(
                () -> new ResourceNotFoundException("Cart", "username", "CARTS_MS")
        );
    }
}
