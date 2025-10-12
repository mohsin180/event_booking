package com.mohsin.booking.services;

import com.mohsin.booking.domain.CreateEventRequest;
import com.mohsin.booking.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest request);

    Page<Event> listEventsOfOrganizer(UUID organizerId, Pageable pageable);

    Event getEvent(UUID eventId, UUID organizerId);

    Event updateEvent(UUID eventId, UUID organizerId, CreateEventRequest request);
}
