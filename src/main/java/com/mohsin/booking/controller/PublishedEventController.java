package com.mohsin.booking.controller;

import com.mohsin.booking.domain.dtos.EventResponseDto;
import com.mohsin.booking.domain.entity.Event;
import com.mohsin.booking.mapper.EventMapper;
import com.mohsin.booking.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/publishedEvents")
@RequiredArgsConstructor
public class PublishedEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<EventResponseDto>> getPublishedEvents(
            @RequestParam(required = false) String query,
            Pageable pageable) {
        Page<Event> events;
        if (query != null && !query.trim().isEmpty()) {
            events = eventService.searchEvents(query, pageable);
        } else {
            events = eventService.getPublishedEvents(pageable);
        }
        return ResponseEntity.ok(events.map(eventMapper::toResponse));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> getResponse(@PathVariable UUID eventId) {
        Optional<Event> publishedEventById = eventService.getPublishedEventById(eventId);
        return ResponseEntity.ok(publishedEventById.map(eventMapper::toResponse)
                .orElse(null));
    }
}
