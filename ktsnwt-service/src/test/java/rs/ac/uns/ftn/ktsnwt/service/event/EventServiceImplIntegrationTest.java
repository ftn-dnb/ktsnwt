package rs.ac.uns.ftn.ktsnwt.service.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import rs.ac.uns.ftn.ktsnwt.dto.SearchEventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.SetSectorPriceDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.EventNotFoundException;
import rs.ac.uns.ftn.ktsnwt.exception.HallNotFoundException;
import rs.ac.uns.ftn.ktsnwt.exception.SectorNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.*;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;
import rs.ac.uns.ftn.ktsnwt.repository.EventRepository;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

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

    @Test(expected = EventNotFoundException.class)
    public void whenSetEventPricingThrowEventNotFound() {
        eventService.setEventPricing(EventConstants.NON_EXISTING_DB_ID, null);
    }

    @Test(expected = ApiRequestException.class)
    public void whenSetEventPricingEmptyPricingList() {
        eventService.setEventPricing(EventConstants.DB_1_ID, new ArrayList<>());
    }

    @Test(expected = SectorNotFoundException.class)
    @Transactional
    public void whenSetEventPricingThrowSectorNotFound() {
        final Long nonExistingSectorId = 1234L;

        List<SetSectorPriceDTO> pricing = new ArrayList<>();
        SetSectorPriceDTO price1 = new SetSectorPriceDTO();
        price1.setId(nonExistingSectorId);
        pricing.add(price1);

        eventService.setEventPricing(EventConstants.DB_1_ID, pricing);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenSetEventPricing() {
        final Long sectorId = 1L;
        List<SetSectorPriceDTO> pricing = new ArrayList<>();
        SetSectorPriceDTO price1 = new SetSectorPriceDTO();
        price1.setId(sectorId);
        price1.setPrice(1000);
        pricing.add(price1);

        Event returnedEvent = eventService.setEventPricing(EventConstants.DB_1_ID, pricing);

        List<EventDay> eventDaysList = returnedEvent.getEventDays().stream().collect(Collectors.toList());

        for (EventDay ed : eventDaysList) {
            assertEquals(1, ed.getPricings().size());

            Pricing price = ed.getPricings().stream().findFirst().orElse(null);
            assertEquals(1000, price.getPrice(), 0.1);
            assertEquals(sectorId, price.getSector().getId());
        }
    }

    @Test
    public void whenFilterEventsReturnNone() {
        SearchEventDTO filter = new SearchEventDTO();
        filter.setEndDate("20-01-2020");
        filter.setLocation("Lokacija");
        filter.setName("Ime");
        filter.setType(EventType.CONCERT);

        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
        assertEquals(0, events.getContent().size());
    }

    @Test(expected = ApiRequestException.class)
    public void whenFilterEventsDateParseFailed() throws ParseException {
        SearchEventDTO filter = new SearchEventDTO();
        filter.setEndDate("20.01.2020");
        filter.setLocation("Lokacija");
        filter.setName("Ime");
        filter.setType(EventType.CONCERT);

        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
    }

    @Test
    public void whenFilterEventsReturnSpecificEvent() {
        SearchEventDTO filter = new SearchEventDTO();
        filter.setEndDate("20-01-2020");
        filter.setLocation("SPENS");
        filter.setName("Koncert");
        filter.setType(EventType.CONCERT);

        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
        assertEquals(1, events.getContent().size());
        assertEquals(Long.valueOf(1L), events.getContent().get(0).getId());
        assertEquals("Koncert", events.getContent().get(0).getName());
    }

    @Test
    public void whenFilterEventsJustDateReturnAll() {
        SearchEventDTO filter = new SearchEventDTO();
        filter.setEndDate("20-01-2020");

        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
        assertEquals(2, events.getContent().size());
    }
}
