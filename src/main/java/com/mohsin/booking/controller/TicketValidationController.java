package com.mohsin.booking.controller;

import com.mohsin.booking.domain.entity.TicketValidation;
import com.mohsin.booking.domain.entity.TicketValidationMethod;
import com.mohsin.booking.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {
    private final TicketValidationService ticketValidationService;

    public ResponseEntity<TicketValidation> validateTicket(@RequestBody TicketValidation ticketValidation) {
        TicketValidationMethod validationMethod = ticketValidation.getValidationMethod();
        TicketValidation validation;
        if (validationMethod.equals(TicketValidationMethod.MANUAL)) {
            validation = ticketValidationService.validateTicketManually(ticketValidation.getId());
        } else {
            validation = ticketValidationService.validateTicketByQrCode(ticketValidation.getId());
        }
        return ResponseEntity.ok(validation);
    }
}
