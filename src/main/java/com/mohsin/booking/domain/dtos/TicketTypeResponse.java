package com.mohsin.booking.domain.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketTypeResponse(
        UUID id,
        String name,
        Double price,
        Integer totalAvailable,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
