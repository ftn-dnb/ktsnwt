package rs.ac.uns.ftn.ktsnwt.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.ktsnwt.constants.EventConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.*;
import rs.ac.uns.ftn.ktsnwt.exception.ErrorMessage;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;
import rs.ac.uns.ftn.ktsnwt.repository.EventRepository;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.ktsnwt.utils.RestResponsePage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EventRepository eventRepository;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(headers);
    }

    @Test
    public void whenGetAllEventsReturnEvents() {
        final int page = 0;
        final int size = 5;

        ParameterizedTypeReference<RestResponsePage<EventDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<EventDTO>>() {};

        ResponseEntity<RestResponsePage<EventDTO>> response = restTemplate.exchange(
                "/api/event/public/all?page=" + page + "&size=" + size, HttpMethod.GET, null, responseType);

        List<EventDTO> events = response.getBody().getContent();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(EventConstants.DB_SIZE, events.size());
        assertEquals(EventConstants.DB_1_ID, events.get(0).getId());
        assertEquals(EventConstants.DB_2_ID, events.get(1).getId());
    }

    @Test
    public void whenGetAllEventsReturnEmptyPage() {
        final int page = 1;
        final int size = 5;

        ParameterizedTypeReference<RestResponsePage<EventDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<EventDTO>>() {};

        ResponseEntity<RestResponsePage<EventDTO>> response = restTemplate.exchange(
                "/api/event/public/all?page=" + page + "&size=" + size, HttpMethod.GET, null, responseType);

        List<EventDTO> events = response.getBody().getContent();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(events.isEmpty());
        assertEquals(EventConstants.DB_SIZE, response.getBody().getTotalElements());
    }

    private HttpEntity<EventDTO> createEventDtoRequest(EventDTO eventDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(eventDto, headers);
    }

    private EventDTO createEventDto() {
        EventDTO eventDto = new EventDTO();
        eventDto.setStartDate("03-03-2003");
        eventDto.setEndDate("04-03-2003");
        eventDto.setTicketsPerUser(2);
        eventDto.setPurchaseLimit(2);
        eventDto.setDescription("This is description");
        eventDto.setHallId(1L);
        eventDto.setName("EventName");
        eventDto.setType(EventType.CONCERT);

        return eventDto;
    }

    @Test
    public void whenAddEventInvalidStartDate() {
        EventDTO eventDto = createEventDto();
        eventDto.setStartDate("03.03.2003.");

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addEvent", HttpMethod.POST, createEventDtoRequest(eventDto), ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid date format", response.getBody().getMessage());
    }

    @Test
    public void whenAddEventInvalidEndDate() {
        EventDTO eventDto = createEventDto();
        eventDto.setEndDate("03.03.2003.");

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addEvent", HttpMethod.POST, createEventDtoRequest(eventDto), ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid date format", response.getBody().getMessage());
    }

    @Test
    public void whenAddEventEndDateIsBeforeStartDate() {
        EventDTO eventDto = createEventDto();
        eventDto.setStartDate("03-03-2003");
        eventDto.setEndDate("01-03-2003");

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addEvent", HttpMethod.POST, createEventDtoRequest(eventDto), ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("End date is after start date", response.getBody().getMessage());
    }

    @Test
    public void whenAddEventHallNotFound() {
        EventDTO eventDto = createEventDto();
        eventDto.setHallId(1234567L);

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addEvent", HttpMethod.POST, createEventDtoRequest(eventDto), ErrorMessage.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenAddEventHallIsOccupied() {
        EventDTO eventDto = createEventDto();
        eventDto.setStartDate("29-11-2019");
        eventDto.setEndDate("30-11-2019");
        eventDto.setHallId(1L);

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addEvent", HttpMethod.POST, createEventDtoRequest(eventDto), ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Hall 1 is occupied", response.getBody().getMessage());
    }

    @Test
    public void whenAddEvent() {
        EventDTO eventDto = createEventDto();

        ResponseEntity<EventDTO> response = restTemplate.exchange(
                "/api/event/addEvent", HttpMethod.POST, createEventDtoRequest(eventDto), EventDTO.class);

        EventDTO createdEvent = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(createdEvent);
        assertNotNull(createdEvent.getId());
        assertEquals(eventDto.getName(), createdEvent.getName());
        assertEquals(eventDto.getDescription(), createdEvent.getDescription());
        assertEquals(eventDto.getHallId(), createdEvent.getHallId());
        assertEquals(eventDto.getType(), createdEvent.getType());
        assertEquals(eventDto.getTicketsPerUser(), createdEvent.getTicketsPerUser());
        assertEquals(eventDto.getPurchaseLimit(), createdEvent.getPurchaseLimit());
        //assertEquals(eventDto.getStartDate(), createdEvent.getStartDate());
        //assertEquals(eventDto.getEndDate(), createdEvent.getEndDate());

        eventRepository.deleteById(createdEvent.getId());
    }

    private HttpEntity<EventEditDTO> createEventEditDtoRequest(EventEditDTO eventEditDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(eventEditDTO, headers);
    }

    @Test
    public void whenEditEventEventNotFound() {
        EventEditDTO eventEditDto = new EventEditDTO();
        eventEditDto.setId(EventConstants.NON_EXISTING_DB_ID);
        eventEditDto.setPurchaseLimit(14);
        eventEditDto.setTicketsPerUser(14);
        eventEditDto.setDescription("This is new description");

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/editEvent", HttpMethod.PUT, createEventEditDtoRequest(eventEditDto), ErrorMessage.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Invalid id of event", response.getBody().getMessage());
    }

    @Test
    @Transactional @Rollback(true)
    public void whenEditEvent() {
        final String newDescription = "This is new description for event";
        final int newPurchaseLimit = 14;
        final int newTicketsPerUser = 14;

        EventEditDTO eventEditDto = new EventEditDTO();
        eventEditDto.setId(EventConstants.DB_2_ID);
        eventEditDto.setPurchaseLimit(newPurchaseLimit);
        eventEditDto.setTicketsPerUser(newTicketsPerUser);
        eventEditDto.setDescription(newDescription);

        ResponseEntity<EventDTO> response = restTemplate.exchange(
                "/api/event/editEvent", HttpMethod.PUT, createEventEditDtoRequest(eventEditDto), EventDTO.class);

        EventDTO editedEvent = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(editedEvent);
        assertEquals(newDescription, editedEvent.getDescription());
        assertEquals(newPurchaseLimit, editedEvent.getPurchaseLimit().intValue());
        assertEquals(newTicketsPerUser, editedEvent.getTicketsPerUser().intValue());
    }

    private HttpEntity<List<SetSectorPriceDTO>> createEventPricingRequest(List<SetSectorPriceDTO> pricing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(pricing, headers);
    }

    @Test
    public void whenAddPricingThrowEventNotFound() {
        List<SetSectorPriceDTO> pricings = new ArrayList<>();
        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addPricing/" + EventConstants.NON_EXISTING_DB_ID, HttpMethod.PUT, createEventPricingRequest(pricings), ErrorMessage.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid id of event", response.getBody().getMessage());
    }

    @Test
    public void whenAddPricingEmptyPricingList() {
        List<SetSectorPriceDTO> pricings = new ArrayList<>();
        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addPricing/" + EventConstants.DB_1_ID, HttpMethod.PUT, createEventPricingRequest(pricings), ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void whenAddPricingThrowSectorNotFound() {
        List<SetSectorPriceDTO> pricings = new ArrayList<>();
        SetSectorPriceDTO pricing1 = new SetSectorPriceDTO();
        pricing1.setId(12344356L); pricing1.setPrice(1000);
        pricings.add(pricing1);

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/event/addPricing/" + EventConstants.DB_1_ID, HttpMethod.PUT, createEventPricingRequest(pricings), ErrorMessage.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid id of sector", response.getBody().getMessage());
    }

    @Test
    @Transactional @Rollback(true)
    public void whenAddPricing() {
        List<SetSectorPriceDTO> pricings = new ArrayList<>();
        SetSectorPriceDTO pricing1 = new SetSectorPriceDTO();
        pricing1.setId(1L); pricing1.setPrice(1000);
        pricings.add(pricing1);

        ResponseEntity<EventDetailedDTO> response = restTemplate.exchange(
                "/api/event/addPricing/" + EventConstants.DB_1_ID, HttpMethod.PUT, createEventPricingRequest(pricings), EventDetailedDTO.class);

        EventDetailedDTO responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals(EventConstants.DB_1_ID, responseBody.getId());
        assertEquals(EventConstants.DB_1_NAME, responseBody.getName());
        assertNotNull(responseBody.getOneDay().getPricing().get(0).getId());
        assertEquals(1000, responseBody.getOneDay().getPricing().get(0).getPrice(), 0.1);
    }
}
