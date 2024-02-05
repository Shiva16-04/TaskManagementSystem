package com.Apiwiz.taskmanagementapi.exceptions;

public class PasswordMinimumRequirementNotMatchException extends RuntimeException{
    public PasswordMinimumRequirementNotMatchException(String message) {
        super(message);
    }
}
