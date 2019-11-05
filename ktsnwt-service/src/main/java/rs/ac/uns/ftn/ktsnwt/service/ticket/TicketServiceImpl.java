package rs.ac.uns.ftn.ktsnwt.service.ticket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.repository.TicketRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketServiceImpl implements TicketService1 {

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public Ticket findById(Long id) {
        try {
            return ticketRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ApiRequestException("Ticket with id '" + id + "' doesn't exist.");
        }
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
