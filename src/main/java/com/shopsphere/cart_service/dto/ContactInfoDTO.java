package com.shopsphere.cart_service.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "contact")
public class ContactInfoDTO {

    private String message;

    private Map<String, String> contactDetails;

    private List<String> onCallSupport;
}
