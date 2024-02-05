package com.Apiwiz.taskmanagementapi.exceptions;

public class InvalidEmailVerificationCodeException extends RuntimeException{
    public InvalidEmailVerificationCodeException(String message) {
        super(message);
    }
}
