package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.ktsnwt.service.eventday.EventDayService;

@RestController
@RequestMapping("/api/eventDay")
public class EventDayController {

    @Autowired
    EventDayService eventDayService;

    @PostMapping("/disable/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> disableEvent(@PathVariable("id") Long id){
        eventDayService.disableEventDay(id);
        return new ResponseEntity<>("Event day canceled",HttpStatus.OK);
    }
}
