package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.*;
import rs.ac.uns.ftn.ktsnwt.mappers.EventMapper;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.service.event.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {


    @Autowired  EventService eventService;

    @PostMapping("/addEvent")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDTO> addEvent(@Valid @RequestBody EventDTO event){
        Event e = eventService.addEvent(event);
        
        return new ResponseEntity<>(EventMapper.toDTO(e), HttpStatus.OK);
    }

    @GetMapping("/public/search")
    public ResponseEntity<Page<EventDTO>> searchEvents(Pageable pageable,
                                                       @Valid @RequestBody SearchEventDTO filter){
        return new ResponseEntity<>(eventService.filterEvents(filter,pageable),HttpStatus.OK);
    }

    @GetMapping("/public/all")
    public ResponseEntity<Page<EventDTO>> getAllEvents(Pageable pageable){
        return new ResponseEntity<>(eventService.getAllEvents(pageable),HttpStatus.OK);
    }

    @PutMapping ("/editEvent")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDTO> editEvent(@RequestBody EventEditDTO event){
        return new ResponseEntity<>(eventService.editEvent(event), HttpStatus.OK);
    }

    @PutMapping("/addPricing/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDetailedDTO> setPricing( @PathVariable("id") Long id,
                                                        @RequestBody List<SetSectorPriceDTO> pricing){
        return new ResponseEntity<>(eventService.setEventPricing(id, pricing), HttpStatus.OK);
    }
}
