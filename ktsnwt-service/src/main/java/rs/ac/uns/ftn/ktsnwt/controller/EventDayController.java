package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.EventDayDTO;
import rs.ac.uns.ftn.ktsnwt.dto.EventDayDescriptionEditDTO;
import rs.ac.uns.ftn.ktsnwt.service.eventday.EventDayService;

@RestController
@RequestMapping("/api/eventDay")
public class EventDayController {

    @Autowired
    EventDayService eventDayService;

    @PostMapping("/disable/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> disableEventDay(@PathVariable("id") Long id){
        eventDayService.disableEventDay(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/description")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDayDTO> changeDescription(@RequestBody EventDayDescriptionEditDTO dto){
        return new ResponseEntity<>(new EventDayDTO(eventDayService.changeDescription(dto)),HttpStatus.OK);
    }
}
