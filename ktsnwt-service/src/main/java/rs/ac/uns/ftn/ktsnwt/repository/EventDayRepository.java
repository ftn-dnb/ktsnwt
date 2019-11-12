package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;

public interface EventDayRepository extends JpaRepository<EventDay, Long> {

    EventDay getById(Long id);
}
