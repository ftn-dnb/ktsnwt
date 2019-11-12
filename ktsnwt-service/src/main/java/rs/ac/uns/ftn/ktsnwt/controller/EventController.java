package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.SearchEventDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.EventMapper;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.service.event.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {


    @Autowired  EventService eventService;

    @PostMapping("/addEvent")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO event){
        Event e = eventService.addEvent(event);
        return new ResponseEntity<>(EventMapper.toDTO(e), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventDTO>> getAllEvents(@RequestParam (name = "page") int page,
                                                       @RequestParam (name = "size") int size,
                                                       @RequestBody SearchEventDTO filter){
        List<Event> events = eventService.filterEvents(filter,page,size);
        return new ResponseEntity<>(EventMapper.toListDto(events),HttpStatus.OK);
    }
}
