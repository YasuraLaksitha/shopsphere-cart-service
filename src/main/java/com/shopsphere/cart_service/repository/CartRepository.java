package com.shopsphere.cart_service.repository;

import com.shopsphere.cart_service.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {

    Optional<CartEntity> findByCreatedBy(String createdBy);
}
