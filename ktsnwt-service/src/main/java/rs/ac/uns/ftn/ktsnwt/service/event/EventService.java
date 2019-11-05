package rs.ac.uns.ftn.ktsnwt.service.event;

import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.model.Event;

public interface EventService {
    Event addEvent(EventDTO event);
}
