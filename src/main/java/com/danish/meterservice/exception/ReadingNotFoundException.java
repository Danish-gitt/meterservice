package com.danish.meterservice.exception;

public class ReadingNotFoundException extends RuntimeException {
    public ReadingNotFoundException(String message) {
        super(message);
    }
}
