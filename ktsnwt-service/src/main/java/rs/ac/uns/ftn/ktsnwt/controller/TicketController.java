package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
