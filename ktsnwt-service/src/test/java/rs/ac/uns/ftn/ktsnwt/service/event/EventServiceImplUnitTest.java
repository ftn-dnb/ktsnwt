package rs.ac.uns.ftn.ktsnwt.service.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.ktsnwt.constants.EventConstants;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.EventEditDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.EventNotFoundException;
import rs.ac.uns.ftn.ktsnwt.exception.HallNotFoundException;
import rs.ac.uns.ftn.ktsnwt.mappers.EventMapper;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.repository.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceImplUnitTest {

    @Autowired
    private EventServiceImpl eventService;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private EventDayRepository eventDayRepository;

    @MockBean
    private HallRepository hallRepository;

    @MockBean
    private SectorRepository sectorRepository;

    @MockBean
    private PricingRepository pricingRepository;

    @MockBean
    private EventMapper eventMapper;

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
        eventDto.setStartDate("03-03-2019");
        eventDto.setEndDate("04-03-2019");
        eventDto.setHallId(1L);

        Mockito.when(eventRepository.checkCapturedHall(any(Date.class), any(Date.class), any(Long.class))).thenReturn(3L);
        eventService.addEvent(eventDto);
    }

    @Test(expected = HallNotFoundException.class)
    public void whenAddEventHallNotFound() {
        EventDTO eventDto = new EventDTO();
        eventDto.setStartDate("03-03-2019");
        eventDto.setEndDate("04-03-2019");
        eventDto.setHallId(1L);

        Mockito.when(eventRepository.checkCapturedHall(new Date(), new Date(), 1L)).thenReturn(0L);
        Mockito.when(hallRepository.findById(1L)).thenReturn(Optional.empty());

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

        Mockito.when(eventRepository.checkCapturedHall(new Date(), new Date(), 1L)).thenReturn(0L);
        Mockito.when(hallRepository.getById(EventConstants.NEW_DB_HALL_ID)).thenReturn(hall);

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
        Mockito.when(eventRepository.findById(EventConstants.MOCK_ID)).thenReturn(Optional.empty());
        eventService.setNewEventImage("new-image-path", EventConstants.MOCK_ID);
    }

    @Test
    public void whenSetNewEventImage() {
        final String imagePath = "new-image-path";
        Event event = new Event();
        event.setId(EventConstants.MOCK_ID);

        Mockito.when(eventRepository.findById(EventConstants.MOCK_ID)).thenReturn(Optional.of(event));
        eventService.setNewEventImage(imagePath, EventConstants.MOCK_ID);

        assertEquals(imagePath, event.getImagePath());
    }

    @Test(expected = EventNotFoundException.class)
    public void whenEditEventThrowEventNotFound() {
        Mockito.when(eventRepository.findById(EventConstants.MOCK_ID)).thenReturn(Optional.empty());

        EventEditDTO eventDto = new EventEditDTO();
        eventDto.setId(EventConstants.MOCK_ID);

        eventService.editEvent(eventDto);
    }

    @Test
    public void whenEditEvent() {
        Event event = new Event();
        event.setId(EventConstants.MOCK_ID);
        event.setTicketsPerUser(2);
        event.setDescription("Event description");
        event.setPurchaseLimit(2);

        Mockito.when(eventRepository.findById(EventConstants.MOCK_ID)).thenReturn(Optional.of(event));

        final String newDescription = "This is new description for event";
        final int newPurchaseLimit = 14;
        final int newTicketsPerUser = 14;

        EventEditDTO eventEditDto = new EventEditDTO();
        eventEditDto.setId(EventConstants.MOCK_ID);
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
        List<Event> events = new ArrayList<>();
        Event event1 = new Event(); event1.setId(1L);
        Event event2 = new Event(); event2.setId(2L);
        events.add(event1);
        events.add(event2);

        Pageable pageRequest = PageRequest.of(0, 5);

        Mockito.when(eventRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(events));

        Page<Event> returnedEvents = eventService.getAllEvents(pageRequest);

        assertEquals(events.size(), returnedEvents.getContent().size());
        assertEquals(events.get(0).getId(), returnedEvents.getContent().get(0).getId());
        assertEquals(events.get(1).getId(), returnedEvents.getContent().get(1).getId());
    }
}
