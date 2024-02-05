package com.Apiwiz.taskmanagementapi.exceptions;

public class MaximumLimitReachedException extends RuntimeException{
    public MaximumLimitReachedException(String message) {
        super(message);
    }
}
