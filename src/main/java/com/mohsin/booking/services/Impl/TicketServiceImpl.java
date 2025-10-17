package com.mohsin.booking.services.Impl;

import com.mohsin.booking.domain.entity.Ticket;
import com.mohsin.booking.repo.TicketRepository;
import com.mohsin.booking.repo.UserRepository;
import com.mohsin.booking.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listPurchaseTickets(UUID userId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(userId, pageable);
    }

    @Override
    public Optional<Ticket> findByIdAndPurchaserId(UUID ticketId, UUID userId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId, userId);
    }
}
