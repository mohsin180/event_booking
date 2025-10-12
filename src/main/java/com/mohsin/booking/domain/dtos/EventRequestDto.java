package com.mohsin.booking.domain.dtos;

import com.mohsin.booking.domain.entity.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record EventRequestDto(
        @NotBlank(message = "Event Name is required")
        String name,
        LocalDateTime start,
        LocalDateTime end,
        @NotBlank(message = "Event venue is required")
        String venue,
        LocalDateTime salesStart,
        LocalDateTime salesEnd,
        @NotNull(message = "Event Status is required")
        EventStatusEnum status,
        @NotEmpty(message = "At least one ticket type is required")
        @Valid
        List<TicketTypeRequestDto> ticketTypes
) {
}
