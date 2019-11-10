package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.ktsnwt.model.Event;


import java.util.Date;


public interface EventRepository extends JpaRepository<Event, Long> {


    @Query("SELECT COUNT(e) FROM Event e WHERE (e.startDate BETWEEN ?1 AND ?2) OR (e.endDate BETWEEN ?1 AND ?2)" +
            "AND e.hall.id = ?3")
    Long checkCapturedHall(Date startDate, Date endDate, Long hallId);
}
