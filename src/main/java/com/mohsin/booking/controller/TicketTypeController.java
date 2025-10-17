package com.mohsin.booking.controller;

import com.mohsin.booking.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events/{eventId}/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    @PostMapping("{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchaseTickets(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        ticketTypeService.purchaseTicket(userId, ticketTypeId);
        return ResponseEntity.ok().build();
    }
}
