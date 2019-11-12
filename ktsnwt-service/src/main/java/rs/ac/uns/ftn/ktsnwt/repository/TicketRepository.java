package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findAll(Pageable page);
    Page<Ticket> getByUserId(Long id, Pageable page);
    Ticket getByRowAndColumnAndEventDayId(int row, int column, Long eventDayId);
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.pricing.id = ?1 AND t.pricing.sector.id = ?2")
    Long getTicketsCountByPricingAndSector(Long pricingId, Long sectorId);

    //Ticket findById();
    //Ticket findAllByEventDay();
    //Ticket findByDatePurchased();
    }
