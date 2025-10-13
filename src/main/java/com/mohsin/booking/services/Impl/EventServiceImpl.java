package com.mohsin.booking.services.Impl;

import com.mohsin.booking.domain.CreateEventRequest;
import com.mohsin.booking.domain.entity.Event;
import com.mohsin.booking.domain.entity.EventStatusEnum;
import com.mohsin.booking.domain.entity.TicketType;
import com.mohsin.booking.domain.entity.User;
import com.mohsin.booking.exceptions.EventNotFoundException;
import com.mohsin.booking.exceptions.UserNotFoundException;
import com.mohsin.booking.repo.EventRepository;
import com.mohsin.booking.repo.UserRepository;
import com.mohsin.booking.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest request) {
        User organizer = userRepository.findById(organizerId).orElseThrow(
                () -> new UserNotFoundException("User not found with id " + organizerId)
        );
        Event event = new Event();
        List<TicketType> ticketTypeList = request.ticketTypes().stream()
                .map(ticket ->
                        TicketType.builder()
                                .name(ticket.name())
                                .price(ticket.price())
                                .totalAvailable(ticket.totalAvailable())
                                .event(event)
                                .description(ticket.description())
                                .build()
                ).toList();
        event.setName(request.name());
        event.setStart(request.start());
        event.setEnd(request.end());
        event.setVenue(request.venue());
        event.setSalesStart(request.salesStart());
        event.setSalesEnd(request.salesEnd());
        event.setOrganizer(organizer);
        event.setStatus(request.status());
        event.setTicketTypes(ticketTypeList);
        return eventRepository.save(event);
    }

    @Override
    public Page<Event> listEventsOfOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Event getEvent(UUID eventId, UUID organizerId) {
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId).orElseThrow(
                () -> new UserNotFoundException("User not found with id " + eventId)
        );
    }

    @Override
    public Event updateEvent(UUID eventId, UUID organizerId, CreateEventRequest request) {
        Event event = eventRepository.findByIdAndOrganizerId(eventId, organizerId).orElseThrow(
                () -> new EventNotFoundException("Event not found with id " + eventId)
        );
        event.setName(request.name());
        event.setStart(request.start());
        event.setEnd(request.end());
        event.setVenue(request.venue());
        event.setSalesStart(request.salesStart());
        event.setSalesEnd(request.salesEnd());
        event.setStatus(request.status());
        List<TicketType> tickets = event.getTicketTypes()
                .stream()
                .map(ticket -> TicketType.builder()
                        .name(ticket.getName())
                        .price(ticket.getPrice())
                        .description(ticket.getDescription())
                        .totalAvailable(ticket.getTotalAvailable())
                        .event(event)
                        .build()
                ).toList();
        event.setTicketTypes(tickets);
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(UUID organizerId, UUID eventId) {
        eventRepository.findByIdAndOrganizerId(eventId, organizerId).
                ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> getPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEventById(UUID eventId) {
        return eventRepository.findByIdAndStatus(eventId, EventStatusEnum.PUBLISHED);
    }
}
