package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.model.Event;

public class EventMapper {

    private EventMapper(){

    }

    public static Event toEntity(EventDTO event){
        Event e = new Event();
        e.setName(event.getName());
        e.setType(event.getType());
        e.setTicketsPerUser(event.getTicketsPerUser());
        e.setPurchaseLimit(event.getPurchaseLimit());
        e.setDescription(event.getDescription());
        return e;
    }

    public static EventDTO toDTO(Event event){
        return new EventDTO(event);
    }
}
