package com.mohsin.booking.mapper;

import com.mohsin.booking.domain.CreateEventRequest;
import com.mohsin.booking.domain.CreateTicketTypeRequest;
import com.mohsin.booking.domain.dtos.EventRequestDto;
import com.mohsin.booking.domain.dtos.EventResponseDto;
import com.mohsin.booking.domain.dtos.TicketTypeRequestDto;
import com.mohsin.booking.domain.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateEventRequest toCreateEventRequest(EventRequestDto requestDto);

    EventResponseDto toResponse(Event event);

    CreateTicketTypeRequest toCreateTicketTypeRequest(TicketTypeRequestDto requestDto);
}
