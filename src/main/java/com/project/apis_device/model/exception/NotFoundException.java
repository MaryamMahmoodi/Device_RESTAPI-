package com.project.apis_device.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException
{

    private String message;

    public NotFoundException()
    {
        this.message = "Record not found";
    }


    public NotFoundException(String message)
    {
        super(message);
    }
}
