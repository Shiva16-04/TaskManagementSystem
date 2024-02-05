package com.Apiwiz.taskmanagementapi.exceptions;

public class PasswordReTypePasswordNotMatchException extends RuntimeException{
    public PasswordReTypePasswordNotMatchException(String message) {
        super(message);
    }
}
