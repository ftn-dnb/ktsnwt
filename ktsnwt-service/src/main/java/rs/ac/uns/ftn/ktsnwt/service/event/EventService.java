package rs.ac.uns.ftn.ktsnwt.service.event;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.SearchEventDTO;
import rs.ac.uns.ftn.ktsnwt.model.Event;

import java.util.List;

public interface EventService {
    Event addEvent(EventDTO event);
    Page<EventDTO> filterEvents(SearchEventDTO filter, Pageable pageable);
    Page<EventDTO> getAllEvents(Pageable pageable);
    void setNewEventImage(String path, Long id);

}
