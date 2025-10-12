package com.mohsin.booking.domain.dtos;

import com.mohsin.booking.domain.entity.EventStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventResponseDto(
        UUID id,
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        LocalDateTime salesStart,
        LocalDateTime salesEnd,
        EventStatusEnum status,
        List<TicketTypeResponse> ticketType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
