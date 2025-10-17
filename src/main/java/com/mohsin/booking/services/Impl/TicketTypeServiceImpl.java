package com.mohsin.booking.services.Impl;

import com.mohsin.booking.domain.entity.Ticket;
import com.mohsin.booking.domain.entity.TicketStatusEnum;
import com.mohsin.booking.domain.entity.TicketType;
import com.mohsin.booking.domain.entity.User;
import com.mohsin.booking.exceptions.TicketSoldOutException;
import com.mohsin.booking.exceptions.TicketTypeNotFoundException;
import com.mohsin.booking.exceptions.UserNotFoundException;
import com.mohsin.booking.repo.TicketRepository;
import com.mohsin.booking.repo.TicketTypeRepository;
import com.mohsin.booking.repo.UserRepository;
import com.mohsin.booking.services.QRCodeService;
import com.mohsin.booking.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QRCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found!")
        );
        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(
                () -> new TicketTypeNotFoundException("TicketType not found!")
        );
        int purchase = ticketRepository.countByTicketTypeId(ticketTypeId);
        Integer totalAvailable = ticketType.getTotalAvailable();
        if (purchase + 1 > totalAvailable) {
            throw new TicketSoldOutException("Tickets have been sold out!");
        }
        Ticket ticket = new Ticket();
        ticket.setPurchaser(user);
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketTypes(ticketType);
        Ticket save = ticketRepository.save(ticket);
        qrCodeService.generateQRCode(save);

        return ticketRepository.save(save);
    }
}
