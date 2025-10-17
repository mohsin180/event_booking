package com.mohsin.booking.services;

import com.mohsin.booking.domain.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {
    Page<Ticket> listPurchaseTickets(UUID userId, Pageable pageable);

    Optional<Ticket> findByIdAndPurchaserId(UUID ticketId, UUID userId);
}
