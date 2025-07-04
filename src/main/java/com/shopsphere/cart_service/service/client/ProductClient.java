package com.shopsphere.cart_service.service.client;

import com.shopsphere.cart_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "products")
public interface ProductClient {

    @GetMapping("/api/public/get")
    ResponseEntity<ProductDTO> getByName(@RequestParam final String productName);

    @GetMapping("/api/public/check/{productName}")
    ResponseEntity<Boolean> checkProductAvailability(@PathVariable final String productName, @RequestParam Integer quantity);
}
