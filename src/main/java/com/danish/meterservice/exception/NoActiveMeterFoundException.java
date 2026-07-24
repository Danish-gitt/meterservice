package com.danish.meterservice.exception;

public class NoActiveMeterFoundException extends RuntimeException{
    public NoActiveMeterFoundException(String message){
        super(message);
    }
}
