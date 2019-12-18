package rs.ac.uns.ftn.ktsnwt.service.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.EventConstants;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.HallNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceImplIntegrationTest {

    @Autowired
    private EventServiceImpl eventService;

    @Value("${user.default-profile-image}")
    private String defaultEventImage;

    @Test(expected = ApiRequestException.class)
    public void whenAddEventWithInvalidDateFormat() {
        EventDTO eventDto = new EventDTO();
        eventDto.setStartDate("01.01.2019.");
        eventDto.setEndDate("02.01.2019.");
        eventService.addEvent(eventDto);
    }

    @Test(expected = ApiRequestException.class)
    public void whenAddEventEndDateBeforeStartDate() {
        EventDTO eventDto = new EventDTO();
        eventDto.setStartDate("03-03-2019");
        eventDto.setEndDate("02-02-2019");
        eventService.addEvent(eventDto);
    }

    @Test(expected = ApiRequestException.class)
    public void whenAddEventHallIsOccupied() {
        EventDTO eventDto = new EventDTO();
        eventDto.setStartDate("29-11-2019");
        eventDto.setEndDate("30-11-2019");
        eventDto.setHallId(1L);
        eventService.addEvent(eventDto);
    }

    @Test(expected = HallNotFoundException.class)
    public void whenAddEventHallNotFound() {
        EventDTO eventDto = new EventDTO();
        eventDto.setStartDate("29-11-2019");
        eventDto.setEndDate("30-11-2019");
        eventDto.setHallId(12345L);
        eventService.addEvent(eventDto);
    }

    @Test
    public void whenAddEventCreateEvent() {
        final Hall hall = new Hall();
        hall.setId(EventConstants.NEW_DB_HALL_ID);

        EventDTO eventDto = new EventDTO();
        eventDto.setStartDate(EventConstants.NEW_DB_STARTDATE);
        eventDto.setEndDate(EventConstants.NEW_DB_ENDDATE);
        eventDto.setHallId(EventConstants.NEW_DB_HALL_ID);
        eventDto.setName(EventConstants.NEW_DB_NAME);
        eventDto.setType(EventConstants.NEW_DB_TYPE);
        eventDto.setTicketsPerUser(EventConstants.NEW_DB_TICKETS_PER_USER);
        eventDto.setPurchaseLimit(EventConstants.NEW_DB_PURCHASE_LIMIT);
        eventDto.setDescription(EventConstants.NEW_DB_DESCRIPTION);

        Event event = eventService.addEvent(eventDto);

        assertEquals(EventConstants.NEW_DB_NAME, event.getName());
        assertEquals(EventConstants.NEW_DB_DESCRIPTION, event.getDescription());
        assertEquals(EventConstants.NEW_DB_PURCHASE_LIMIT, event.getPurchaseLimit());
        assertEquals(EventConstants.NEW_DB_TICKETS_PER_USER, event.getTicketsPerUser());
        assertEquals(EventConstants.NEW_DB_TYPE, event.getType());
        assertEquals(EventConstants.NEW_DB_HALL_ID, event.getHall().getId());
        assertEquals(defaultEventImage, event.getImagePath());

        // TODO: kako proveriti datum ??
//        assertEquals(, event.getStartDate());
    }
}
