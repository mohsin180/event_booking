package com.mohsin.booking.exceptions;

public class QRCodeNotFoundException extends RuntimeException {
    public QRCodeNotFoundException(String message) {
        super(message);
    }
}
