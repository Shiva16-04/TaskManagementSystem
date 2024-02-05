package com.Apiwiz.taskmanagementapi.exceptions;

public class InstanceCreationRestrictedException extends RuntimeException{
    public InstanceCreationRestrictedException(String message) {
        super(message);
    }
}
