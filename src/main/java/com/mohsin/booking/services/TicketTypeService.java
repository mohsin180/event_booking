package com.mohsin.booking.services;

import com.mohsin.booking.domain.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
