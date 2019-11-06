package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findAll(Pageable page);
    //Ticket findById();
    //Ticket findAllByEventDay();
    //Ticket findByDatePurchased();
}
