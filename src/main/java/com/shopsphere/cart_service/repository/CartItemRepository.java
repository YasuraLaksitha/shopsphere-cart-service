package com.shopsphere.cart_service.repository;

import com.shopsphere.cart_service.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    Optional<CartItemEntity> findByItemNameEqualsIgnoreCaseAndCartId(String createdBy,Long cartId);
}
