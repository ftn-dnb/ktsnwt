package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.service.event.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {


    @Autowired  EventService eventService;

    @PostMapping("/addEvent")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Event> addEvent(@RequestBody EventDTO event){
        eventService.addEvent(event);
        return ResponseEntity.ok().build();
    }
}
