package com.project.oneglobale_device.model.exception;

public class OperationFailedException extends RuntimeException
{
    private String message;

    public OperationFailedException(String operation) {
        this.message = operation;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
