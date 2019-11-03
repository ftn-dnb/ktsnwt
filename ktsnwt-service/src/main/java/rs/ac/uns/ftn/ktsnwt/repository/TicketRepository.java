package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket getByRowAndColumnAndEventDayId(int row, int column, Long eventDayId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.pricing.id = ?1 AND t.pricing.sector.id = ?2")
    Long getTicketsCountByPricingAndSector(Long pricingId, Long sectorId);
}
