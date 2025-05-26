package com.shopsphere.cart_service.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service")
public class ProductClient {

}
