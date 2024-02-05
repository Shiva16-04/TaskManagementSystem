package com.Apiwiz.taskmanagementapi.exceptions;

public class InvalidTaskCodeException extends RuntimeException{
    public InvalidTaskCodeException(String message) {
        super(message);
    }
}
