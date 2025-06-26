package com.shopsphere.cart_service;

import com.shopsphere.cart_service.dto.ContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = {ContactInfoDTO.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableFeignClients
@OpenAPIDefinition(
        info = @Info(
                title = "Cart Service",
                version = "v1",
                description = "Cart Service for ShopSphere ecommerce platform",
                contact = @Contact(
                        name = "Yasura Laksitha",
                        email = "yasura.dev@gmail.com",
                        url = "https://github.com/YasuraLaksitha/shopsphere-admin-service.git"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Postman collection for user cart operations",
                url = "https://github.com/YasuraLaksitha/shopsphere-cart-service.git"
        )
)
public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }

}
