package com.buildbot.contactsmanagement.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String resource;
    private final String fieldName;
    private final Object value;

    public ResourceNotFoundException(String resource, String fieldName, Object value) {
        super(String.format("The resource '%s' with '%s' = '%s' was not found.", resource, fieldName, value));
        this.resource = resource;
        this.fieldName = fieldName;
        this.value = value;
    }

    public String getResource() {
        return resource;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }
}
