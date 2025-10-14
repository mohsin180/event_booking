package com.mohsin.booking.services;

import com.mohsin.booking.domain.entity.QRCode;
import com.mohsin.booking.domain.entity.Ticket;

public interface QRCodeService {
    QRCode generateQRCode(Ticket ticket);
}
