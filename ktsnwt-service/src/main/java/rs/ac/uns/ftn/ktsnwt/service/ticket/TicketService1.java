package rs.ac.uns.ftn.ktsnwt.service.ticket;

import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import java.util.List;


public interface TicketService1 {
    Ticket findById(Long id);
    List<Ticket> findAll();
}
