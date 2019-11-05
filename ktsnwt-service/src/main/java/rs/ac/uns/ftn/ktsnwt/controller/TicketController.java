package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.service.ticket.TicketService1;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService1 ticketService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TicketDTO>> getTickets() {
        List<Ticket> tickets = ticketService.findAll();

        List<TicketDTO> ticketsDTO = new ArrayList<>();
        for (Ticket t : tickets) {
            ticketsDTO.add(new TicketDTO(t));
        }
        return new ResponseEntity<>(ticketsDTO, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id){
        Ticket ticket = ticketService.findById(id);
        if(ticket == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new TicketDTO(ticket), HttpStatus.OK);
    }
}
