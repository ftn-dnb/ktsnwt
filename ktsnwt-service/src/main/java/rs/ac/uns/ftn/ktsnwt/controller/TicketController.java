package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketsService ticketsService;


    @PostMapping("/reserve")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity reserveTicket(@Valid @RequestBody TicketsToReserveDTO ticketsInfo) {
        ticketsService.reserveTickets(ticketsInfo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity cancelTicket(@PathVariable Long id) {
        ticketsService.cancelTicket(id);
        return ResponseEntity.ok().build();
    }
}
