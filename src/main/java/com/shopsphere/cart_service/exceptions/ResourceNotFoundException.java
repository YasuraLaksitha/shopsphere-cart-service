package com.shopsphere.cart_service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    final String resourceName;

    final String fieldName;

    final String fieldValue;

    public ResourceNotFoundException(final String resourceName, final String fieldName, final String fieldValue) {
        super(String.format("%s not found with %s: '%s'.", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
