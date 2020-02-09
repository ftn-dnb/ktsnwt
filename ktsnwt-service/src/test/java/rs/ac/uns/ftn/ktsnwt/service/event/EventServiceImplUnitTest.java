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
import rs.ac.uns.ftn.ktsnwt.common.TimeProvider;
import rs.ac.uns.ftn.ktsnwt.constants.EventConstants;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.EventEditDTO;
import rs.ac.uns.ftn.ktsnwt.dto.SearchEventDTO;
import rs.ac.uns.ftn.ktsnwt.dto.SetSectorPriceDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.EventNotFoundException;
import rs.ac.uns.ftn.ktsnwt.exception.HallNotFoundException;
import rs.ac.uns.ftn.ktsnwt.exception.SectorNotFoundException;
import rs.ac.uns.ftn.ktsnwt.mappers.EventMapper;
import rs.ac.uns.ftn.ktsnwt.model.*;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;
import rs.ac.uns.ftn.ktsnwt.repository.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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

        Mockito.when(hallRepository.findById(1L)).thenReturn(Optional.of(new Hall()));
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
        Mockito.when(hallRepository.findById(EventConstants.NEW_DB_HALL_ID)).thenReturn(Optional.of(hall));

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

    @Test(expected = EventNotFoundException.class)
    public void whenSetEventPricingThrowEventNotFound() {
        Mockito.when(eventRepository.findById(EventConstants.NON_EXISTING_DB_ID)).thenReturn(Optional.empty());
        eventService.setEventPricing(EventConstants.NON_EXISTING_DB_ID, null);
    }

    @Test(expected = ApiRequestException.class)
    public void whenSetEventPricingEmptyPricingList() {
        Mockito.when(eventRepository.findById(EventConstants.DB_1_ID)).thenReturn(Optional.of(new Event()));
        eventService.setEventPricing(EventConstants.DB_1_ID, new ArrayList<>());
    }

    @Test(expected = SectorNotFoundException.class)
    public void whenSetEventPricingThrowSectorNotFound() {
        Event event = new Event();
        event.setId(EventConstants.MOCK_ID);

        EventDay eventDay1 = new EventDay(); eventDay1.setId(1L);
        EventDay eventDay2 = new EventDay(); eventDay2.setId(2L);
        Set<EventDay> eventDays = new HashSet<>();
        eventDays.add(eventDay1);
        eventDays.add(eventDay2);
        event.setEventDays(eventDays);

        final Long nonExistingSectorId = 1234L;

        List<SetSectorPriceDTO> pricing = new ArrayList<>();
        SetSectorPriceDTO price1 = new SetSectorPriceDTO(); price1.setId(nonExistingSectorId);
        pricing.add(price1);

        Mockito.when(eventRepository.findById(EventConstants.MOCK_ID)).thenReturn(Optional.of(event));
        Mockito.when(sectorRepository.findById(nonExistingSectorId)).thenReturn(Optional.empty());

        eventService.setEventPricing(EventConstants.MOCK_ID, pricing);
    }

    @Test
    public void whenSetEventPricing() {
        Event event = new Event();
        event.setId(EventConstants.MOCK_ID);

        EventDay eventDay1 = new EventDay(); eventDay1.setId(1L);
        EventDay eventDay2 = new EventDay(); eventDay2.setId(2L);
        Set<EventDay> eventDays = new HashSet<>();
        eventDays.add(eventDay1);
        eventDays.add(eventDay2);
        event.setEventDays(eventDays);

        List<SetSectorPriceDTO> pricing = new ArrayList<>();
        SetSectorPriceDTO price1 = new SetSectorPriceDTO(); price1.setId(1L); price1.setPrice(1000);
        pricing.add(price1);

        Sector sector = new Sector();
        sector.setId(1L);

        Mockito.when(eventRepository.findById(EventConstants.MOCK_ID)).thenReturn(Optional.of(event));
        Mockito.when(sectorRepository.findById(any(Long.class))).thenReturn(Optional.of(sector));

        Event returnedEvent = eventService.setEventPricing(EventConstants.MOCK_ID, pricing);

        List<EventDay> eventDaysList = returnedEvent.getEventDays().stream().collect(Collectors.toList());

        for (EventDay ed : eventDaysList) {
            assertEquals(1, ed.getPricings().size());

            Pricing price = ed.getPricings().stream().findFirst().orElse(null);
            assertEquals(1000, price.getPrice(), 0.1);
            assertEquals(sector.getId(), price.getSector().getId());
        }
    }

//    @Test
//    public void whenFilterEventsReturnNone() throws ParseException {
//        SearchEventDTO filter = new SearchEventDTO();
//        filter.setEndDate("20-01-2020");
//        filter.setLocation("Lokacija");
//        filter.setName("Ime");
//        filter.setType(EventType.CONCERT);
//
//        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
//        assertEquals(0, events.getContent().size());
//    }
//
//    @Test(expected = ApiRequestException.class)
//    public void whenFilterEventsDateParseFailed() throws ParseException {
//        SearchEventDTO filter = new SearchEventDTO();
//        filter.setEndDate("20-01-2020");
//        filter.setLocation("Lokacija");
//        filter.setName("Ime");
//        filter.setType(EventType.CONCERT);
//
//        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
//    }
//
//    @Test
//    public void whenFilterEventsTypeNull() throws ParseException {
//        SearchEventDTO filter = new SearchEventDTO();
//        filter.setEndDate("20-01-2020");
//        filter.setLocation("Lokacija");
//        filter.setName("Ime");
//
//        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
//        assertEquals(0, events.getContent().size());
//    }
//
//    @Test
//    public void whenFilterEventsReturnSpecificEvent() throws ParseException {
//        SearchEventDTO filter = new SearchEventDTO();
//        filter.setEndDate("20-01-2020");
//        filter.setLocation("SPENS");
//        filter.setName("Koncert");
//        filter.setType(EventType.CONCERT);
//
//        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
//        assertEquals(1, events.getContent().size());
//        assertEquals(Long.valueOf(1L), events.getContent().get(0).getId());
//        assertEquals("Koncert", events.getContent().get(0).getName());
//    }
//
//    @Test
//    public void whenFilterEventsJustDateReturnAll() throws ParseException {
//        SearchEventDTO filter = new SearchEventDTO();
//        filter.setEndDate("20-01-2020");
//
//        Page<EventDTO> events = eventService.filterEvents(filter, PageRequest.of(0, 5));
//        assertEquals(2, events.getContent().size());
//    }
}
