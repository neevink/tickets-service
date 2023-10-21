package ru.egormit.starshipservice.service;

import ru.itmo.library.CreateEventRequest;
import ru.itmo.library.EventDto;

import java.util.List;

public interface EventService {
    EventDto createEvent(CreateEventRequest request);

    List<EventDto> getAllEvents();

    EventDto getEventById(Long ticketId);

    void deleteEventById(Long eventId);

    void updateEventById(Long eventId, CreateEventRequest request);

    long countEvents();
}
