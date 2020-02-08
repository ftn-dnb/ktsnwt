package rs.ac.uns.ftn.ktsnwt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.EventConstants;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventRepositoryIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void whenFindAllReturnEvents() {
        Page<Event> events = eventRepository.findAll(PageRequest.of(0, 5));
        assertEquals(EventConstants.DB_SIZE, events.getTotalElements());
        assertEquals(EventConstants.DB_SIZE, events.getContent().size());
        assertEquals(EventConstants.DB_1_ID, events.getContent().get(0).getId());
        assertEquals(EventConstants.DB_2_ID, events.getContent().get(1).getId());
    }

    @Test
    public void whenFindAllReturnEmptyPage() {
        Page<Event> events = eventRepository.findAll(PageRequest.of(1, 5));
        assertEquals(EventConstants.DB_SIZE, events.getTotalElements());
        assertTrue(events.getContent().isEmpty());
    }

    @Test
    public void whenCheckCapturedHallReturnHallIsCaptured() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate =  dateFormat.parse("29-11-2019");
        Date endDate =  dateFormat.parse("30-11-2019");;
        Long hallId = 1L;
        Long result = eventRepository.checkCapturedHall(startDate, endDate, hallId);
        assertTrue(result > 0);
    }

    @Test
    public void whenCheckCapturedHallReturnHallNotCaptured() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = dateFormat.parse("14-11-2019");
        Date endDate = dateFormat.parse("14-11-2019");;
        Long hallId = 1L;
        Long result = eventRepository.checkCapturedHall(startDate, endDate, hallId);
        assertEquals(0, result.longValue());
    }

    @Test
    public void whenFilterEventsNoEndDateReturnNone() {
        Page<Event> events = eventRepository.filterEvents(null, 1, "Name", "Name", PageRequest.of(0, 5));
        assertEquals(0, events.getContent().size());
    }

    @Test
    public void whenFilterEventsJustLocationName() {
        Date now = new Date();
        Page<Event> events = eventRepository.filterEvents(now, null, "SPENS", null, PageRequest.of(0, 5));
        assertEquals(2, events.getContent().size());
        assertEquals("SPENS", events.getContent().get(0).getHall().getLocation().getName());
    }

    @Test
    public void whenFilterEventsJustEventType() {
        Date now = new Date();
        Page<Event> events = eventRepository.filterEvents(now, EventType.CONCERT.ordinal(), null, null, PageRequest.of(0, 5));
        assertEquals(2, events.getContent().size());
        assertEquals("SPENS", events.getContent().get(0).getHall().getLocation().getName());
        assertEquals("SPENS", events.getContent().get(1).getHall().getLocation().getName());
    }

    @Test
    public void whenFilterEventsJustEventName() {
        Date now = new Date();
        Page<Event> events = eventRepository.filterEvents(now, null, null, "Koncert", PageRequest.of(0, 5));
        assertEquals(1, events.getContent().size());
        assertEquals("Koncert", events.getContent().get(0).getName());
    }

    @Test
    public void whenFilterEventsReturnNone() {
        Date now = new Date();
        Page<Event> events = eventRepository.filterEvents(now, 1, "Lokacija1", "Ime", PageRequest.of(0, 5));
        assertEquals(0, events.getContent().size());
    }

    @Test
    public void whenFilterEventsReturnSpecificEvent() {
        Date now = new Date();
        Page<Event> events = eventRepository.filterEvents(now, 1, "SPENS", "Koncert", PageRequest.of(0, 5));
        assertEquals(1, events.getContent().size());
        Event event = events.getContent().get(0);

        assertEquals(Long.valueOf(1L), event.getId());
        assertEquals("SPENS", event.getHall().getLocation().getName());
        assertEquals("Koncert", event.getName());
        assertEquals(EventType.CONCERT, event.getType());
    }

}
