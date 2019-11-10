package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.TicketMapper;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketsService ticketsService;


    @GetMapping("/{page}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<TicketDTO>> getUsersTickets(@PathVariable int page) {
        List<Ticket> tickets = ticketsService.getUsersTickets(page);
        return new ResponseEntity<>(TicketMapper.toListDto(tickets), HttpStatus.OK);
    }

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
