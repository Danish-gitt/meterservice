package com.danish.meterservice.exception;

public class MeterAlreadyExistsException extends RuntimeException{
    public MeterAlreadyExistsException(String message){
        super(message);
    }
}
