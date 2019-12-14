package rs.ac.uns.ftn.ktsnwt.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
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

    public static List<EventDTO> toListDTO(List<Event> events){
        return events.stream().map(EventDTO::new).collect(Collectors.toList());
    }
}
