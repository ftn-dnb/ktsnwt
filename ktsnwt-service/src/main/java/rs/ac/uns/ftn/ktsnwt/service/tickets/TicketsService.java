package rs.ac.uns.ftn.ktsnwt.service.tickets;

import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import java.util.List;

public interface TicketsService {

    List<Ticket> getUsersTickets(int page);
    void reserveTickets(TicketsToReserveDTO ticketsInfo);
    void cancelTicket(Long id);
}
