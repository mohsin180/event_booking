package com.mohsin.booking.services.Impl;

import com.mohsin.booking.domain.entity.*;
import com.mohsin.booking.exceptions.QRCodeNotFoundException;
import com.mohsin.booking.exceptions.TicketNotFoundException;
import com.mohsin.booking.repo.QRCodeRepository;
import com.mohsin.booking.repo.TicketRepository;
import com.mohsin.booking.repo.TicketValidationRepository;
import com.mohsin.booking.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketValidationImpl implements TicketValidationService {
    private final TicketValidationRepository ticketValidationRepository;
    private final QRCodeRepository qrCodeRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QRCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QRCodeStatusEnum.VALID).orElseThrow(
                () -> new QRCodeNotFoundException("QR code " + qrCodeId + " not found")
        );
        Ticket ticket = qrCode.getTicket();
        return getTicketValidation(ticket, TicketValidationMethod.QR_SCAN);

    }

    private TicketValidation getTicketValidation(Ticket ticket, TicketValidationMethod validationMethod) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(validationMethod);
        TicketValidationStatusEnum ticketValidationStatusEnum = ticket.getValidations().stream()
                .filter(validate -> TicketValidationStatusEnum.VALID.equals(validate.getStatus()))
                .findFirst()
                .map(validate -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);
        ticketValidation.setStatus(ticketValidationStatusEnum);
        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new TicketNotFoundException("Ticket " + ticketId + " not found")
        );
        return getTicketValidation(ticket, TicketValidationMethod.MANUAL);
    }
}
