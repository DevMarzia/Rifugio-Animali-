package com.rifugio;
//  classe eccezioni personalizzate

public class ShelterException extends RuntimeException {
    public ShelterException(String message, Throwable cause) {
        super(message, cause);
    }
}
