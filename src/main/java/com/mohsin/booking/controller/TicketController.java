package com.mohsin.booking.controller;

import com.mohsin.booking.domain.entity.Ticket;
import com.mohsin.booking.services.QRCodeService;
import com.mohsin.booking.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final QRCodeService qrCodeService;

    @GetMapping
    public Page<Ticket> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ticketService.listPurchaseTickets(userId, pageable);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> findByIdAndPurchaserId(@PathVariable UUID ticketId
            , @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ticketService.findByIdAndPurchaserId(ticketId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{ticketId}/qr-Codes")
    public ResponseEntity<byte[]> getTicketQRCode(@AuthenticationPrincipal Jwt jwt,
                                                  @PathVariable UUID ticketId) {
        UUID userId = UUID.fromString(jwt.getSubject());
        byte[] qrCodeImage = qrCodeService.getQRCodeImageForUserAndTicket(userId, ticketId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImage.length);
        return ResponseEntity.ok()
                .headers(headers)
                .body(qrCodeImage);
    }
}
