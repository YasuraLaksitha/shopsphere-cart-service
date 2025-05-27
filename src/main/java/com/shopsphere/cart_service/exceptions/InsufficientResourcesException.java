package com.shopsphere.cart_service.exceptions;

public class InsufficientResourcesException extends RuntimeException {

    final String resourceName;

    final String fieldName;

    final String fieldValue;

    public InsufficientResourcesException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("Insufficient resources count for %s  with %s: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
