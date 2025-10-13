package com.mohsin.booking.controller;

import com.mohsin.booking.domain.dtos.EventRequestDto;
import com.mohsin.booking.domain.dtos.EventResponseDto;
import com.mohsin.booking.domain.entity.Event;
import com.mohsin.booking.mapper.EventMapper;
import com.mohsin.booking.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto requestDto,
                                                        @AuthenticationPrincipal Jwt jwt) {
        UUID organizerId = UUID.fromString(jwt.getSubject());
        Event event = eventService.createEvent(organizerId, eventMapper.toCreateEventRequest(requestDto));
        return ResponseEntity.ok()
                .body(eventMapper.toResponse(event));
    }

    @GetMapping
    public ResponseEntity<Page<EventResponseDto>> getListEvent(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
    ) {
        UUID organizerId = UUID.fromString(jwt.getSubject());
        Page<Event> events = eventService.listEventsOfOrganizer(organizerId, pageable);
        return ResponseEntity.ok(events.map(eventMapper::toResponse));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID organizerId = UUID.fromString(jwt.getSubject());
        Event event = eventService.getEvent(eventId, organizerId);
        return ResponseEntity.ok(eventMapper.toResponse(event));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> updateEvent(@AuthenticationPrincipal Jwt jwt,
                                                        @PathVariable UUID eventId,
                                                        @RequestBody @Valid EventRequestDto requestDto) {
        UUID organizerId = UUID.fromString(jwt.getSubject());

        Event event = eventService.updateEvent(eventId, organizerId,
                eventMapper.toCreateEventRequest(requestDto));
        return ResponseEntity.ok(eventMapper.toResponse(event));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId) {
        UUID organizerId = UUID.fromString(jwt.getSubject());
        eventService.deleteEvent(organizerId, eventId);
        return ResponseEntity.noContent().build();
    }

}
