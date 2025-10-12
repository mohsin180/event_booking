package com.mohsin.booking.domain;

import com.mohsin.booking.domain.entity.EventStatusEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CreateEventRequest(
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        LocalDateTime salesStart,
        LocalDateTime salesEnd,
        EventStatusEnum status,
        List<CreateTicketTypeRequest> ticketTypes
) {
}
