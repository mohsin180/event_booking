package com.mohsin.booking.services;

import com.mohsin.booking.domain.entity.QRCode;
import com.mohsin.booking.domain.entity.Ticket;

import java.util.UUID;

public interface QRCodeService {
    QRCode generateQRCode(Ticket ticket);

    byte[] getQRCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
