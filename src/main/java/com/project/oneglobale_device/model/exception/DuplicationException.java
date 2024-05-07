package com.project.oneglobale_device.model.exception;

public class DuplicationException extends RuntimeException
{
    private String message;

    public DuplicationException()
    {
        this.message = "The data already exists";
    }
}
