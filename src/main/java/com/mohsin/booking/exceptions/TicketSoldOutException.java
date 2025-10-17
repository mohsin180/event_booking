package com.mohsin.booking.exceptions;

public class TicketSoldOutException extends RuntimeException {
    public TicketSoldOutException(String message) {
        super(message);
    }
}
