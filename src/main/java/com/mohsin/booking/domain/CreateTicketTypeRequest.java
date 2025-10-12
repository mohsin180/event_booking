package com.mohsin.booking.domain;

import lombok.Builder;

@Builder
public record CreateTicketTypeRequest(
        String name,
        Double price,
        Integer totalAvailable,
        String description
) {
}
