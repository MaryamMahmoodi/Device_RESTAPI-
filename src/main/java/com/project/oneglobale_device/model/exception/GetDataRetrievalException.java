package com.project.oneglobale_device.model.exception;

public class GetDataRetrievalException extends RuntimeException
{
    private String message;

    public GetDataRetrievalException()
    {
        this.message = "Error occurred while retrieving data";
    }
}
