package rs.ac.uns.ftn.ktsnwt.service.event;


import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.SearchEventDTO;
import rs.ac.uns.ftn.ktsnwt.model.Event;

import java.util.List;

public interface EventService {
    Event addEvent(EventDTO event);
    List<Event> filterEvents(SearchEventDTO filter, int page, int size);
    List<Event> getAllEvents(int page, int size);
}
