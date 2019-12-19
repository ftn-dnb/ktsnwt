package rs.ac.uns.ftn.ktsnwt.service.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.ktsnwt.constants.EventConstants;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.EventEditDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.EventNotFoundException;
import rs.ac.uns.ftn.ktsnwt.exception.HallNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.repository.EventRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceImplIntegrationTest {

    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private EventRepository eventRepository;

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
    @Transactional @Rollback(true)
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

    @Test(expected = EventNotFoundException.class)
    public void whenSetNewEventImageThrowEventNotFound() {
        eventService.setNewEventImage("new-image-path", EventConstants.NON_EXISTING_DB_ID);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenSetNewEventImage() {
        final String imagePath = "new-image-path";
        eventService.setNewEventImage(imagePath, EventConstants.DB_1_ID);
        Event event = eventRepository.findById(EventConstants.DB_1_ID).orElseThrow(() -> new EventNotFoundException("Invalid id"));
        assertEquals(imagePath, event.getImagePath());
    }

    @Test(expected = EventNotFoundException.class)
    public void whenEditEventThrowEventNotFound() {
        EventEditDTO eventDto = new EventEditDTO();
        eventDto.setId(EventConstants.NON_EXISTING_DB_ID);
        eventService.editEvent(eventDto);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenEditEvent() {
        final String newDescription = "This is new description for event";
        final int newPurchaseLimit = 14;
        final int newTicketsPerUser = 14;

        EventEditDTO eventEditDto = new EventEditDTO();
        eventEditDto.setId(EventConstants.DB_1_ID);
        eventEditDto.setDescription(newDescription);
        eventEditDto.setTicketsPerUser(newTicketsPerUser);
        eventEditDto.setPurchaseLimit(newPurchaseLimit);

        Event editedEvent = eventService.editEvent(eventEditDto);

        assertEquals(newDescription, editedEvent.getDescription());
        assertEquals(newTicketsPerUser, editedEvent.getTicketsPerUser());
        assertEquals(newPurchaseLimit, editedEvent.getPurchaseLimit());
    }

    @Test
    public void getAllEvents() {
        Page<Event> returnedEvents = eventService.getAllEvents(PageRequest.of(0, 5));

        assertEquals(2, returnedEvents.getContent().size());
        assertEquals(EventConstants.DB_1_ID, returnedEvents.getContent().get(0).getId());
        assertEquals(EventConstants.DB_2_ID, returnedEvents.getContent().get(1).getId());
    }
}
