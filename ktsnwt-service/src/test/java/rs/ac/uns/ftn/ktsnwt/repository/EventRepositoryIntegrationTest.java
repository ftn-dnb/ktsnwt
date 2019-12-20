package rs.ac.uns.ftn.ktsnwt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.EventConstants;
import rs.ac.uns.ftn.ktsnwt.model.Event;

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

}
