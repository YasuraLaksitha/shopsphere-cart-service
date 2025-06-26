package com.shopsphere.cart_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "contact")
@Schema(
        name = "Contact",
        description = "Schema to hold Contact information of crew"
)
public class ContactInfoDTO {

    @Schema(description = "Welcome message", example = "Welcome to ShopSphere application")
    private String message;

    @Schema(description = "Contact information of the crew", example = "[name: Jhone, email: Jhon@Example]")
    private Map<String, String> contactDetails;

    @Schema(description = "Hotline for call support", example = "077-456 45 66")
    private List<String> onCallSupport;
}
