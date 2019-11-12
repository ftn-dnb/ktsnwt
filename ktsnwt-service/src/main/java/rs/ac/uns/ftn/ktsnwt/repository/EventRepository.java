package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;


import java.util.Date;


public interface EventRepository extends JpaRepository<Event, Long> {


    @Query("SELECT COUNT(e) FROM Event e WHERE (e.startDate BETWEEN ?1 AND ?2) OR (e.endDate BETWEEN ?1 AND ?2)" +
            "AND e.hall.id = ?3")
    Long checkCapturedHall(Date startDate, Date endDate, Long hallId);

    Page<Event> findAll(Pageable page);

    @Query("SELECT e FROM Event e WHERE ((e.startDate BETWEEN ?1 AND ?2) AND (e.endDate BETWEEN ?1 AND ?2)) AND" +
            "(?3 is null or e.type = ?3) AND (?4 is null or e.hall.location.id = ?4")
    Page<Event> filterEvents(Date startDate, Date endDate, EventType eventType, Long locationId, Pageable page);



}
