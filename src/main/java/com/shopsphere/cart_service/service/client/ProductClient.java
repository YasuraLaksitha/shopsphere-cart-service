package com.shopsphere.cart_service.service.client;

import com.shopsphere.cart_service.dto.ProductDTO;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "products")
public interface ProductClient {

    @GetMapping("/api/products/public/get")
    ResponseEntity<ProductDTO> getByName(
            @NotEmpty(message = "product name is required") @RequestParam final String productName);
}
