package rs.ac.uns.ftn.ktsnwt.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.LocationConstants;
import rs.ac.uns.ftn.ktsnwt.constants.TicketConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.ktsnwt.service.tickets.TicketsServiceImpl;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TicketsServiceImpl ticketsService;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USER_USERNAME, UserConstants.DB_USER_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private void loginAdmin() {
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
    public void whenValidSizeAndPageParams_returnTicketList() {
        ResponseEntity<TicketDTO[]> response = restTemplate.exchange(
                "/api/tickets/all?page=0&size=5", HttpMethod.GET, createHttpEntity(), TicketDTO[].class
        );
        assertEquals(3, response.getBody().length);
    }

    @Test
    public void givenValidTicketId_returnTicket() {
        ResponseEntity<TicketDTO> response = restTemplate.exchange(
                "/api/tickets/specific/" + TicketConstants.DB_ID_1, HttpMethod.GET, createHttpEntity(), TicketDTO.class
        );

        assertEquals(TicketConstants.DB_COLUMN_NUM_1, response.getBody().getSeat());
        assertEquals(TicketConstants.DB_EVENT_DAY_ID_1,  response.getBody().getEventDayId());
        assertEquals(TicketConstants.DB_ROW_NUM_1, response.getBody().getRow());
        assertEquals(TicketConstants.DB_DATE_1, response.getBody().getDatePurchased().toString());
        assertEquals(TicketConstants.DB_PURCHASED_1, response.getBody().isPurchased());
        assertEquals(TicketConstants.DB_ID_1, response.getBody().getId());
    }

    @Test
    public void givenValidLocationId_returnValidReport() {
        loginAdmin();

        String date = "2019-11-20";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);

        HttpEntity<String> request = new HttpEntity<>(date, headers);


        ResponseEntity<ReportInfoDTO> response = restTemplate.exchange(
                "/api/tickets/locationDailyReport/" + LocationConstants.DB_ID, HttpMethod.POST, request, ReportInfoDTO.class
        );

        ReportInfoDTO report = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(report);

        assertEquals(398, report.getIncome(), 0.05);
        assertEquals(2, report.getTicketsSold());
    }

    @Test
    public void givenValidLocationId_returnValidMothlyReport() {
        loginAdmin();

        String date = "2019-11";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);

        HttpEntity<String> request = new HttpEntity<>(date, headers);


        ResponseEntity<ReportInfoDTO> response = restTemplate.exchange(
                "/api/tickets/locationMonthlyReport/" + LocationConstants.DB_ID, HttpMethod.POST, request, ReportInfoDTO.class
        );

        ReportInfoDTO report = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(report);

        assertEquals(398, report.getIncome(), 0.05);
        assertEquals(2, report.getTicketsSold());
    }

    @Test
    public void givenValidEventId_returnValidReport() {
        loginAdmin();

        String date = "2019-11-20";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);

        HttpEntity<String> request = new HttpEntity<>(date, headers);


        ResponseEntity<ReportInfoDTO> response = restTemplate.exchange(
                "/api/tickets/eventDailyReport/" + 1, HttpMethod.POST, request, ReportInfoDTO.class
        );

        ReportInfoDTO report = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(report);

        assertEquals(398, report.getIncome(), 0.05);
        assertEquals(2, report.getTicketsSold());
    }

    @Test
    public void givenValidPageAndSizeParameters_returnUsersTickets() {
        ResponseEntity<TicketDTO[]> response = restTemplate.exchange(
                "/api/tickets?page=0&size=5", HttpMethod.GET, createHttpEntity(), TicketDTO[].class
        );
        assertEquals(3, response.getBody().length);
    }

    @Test
    public void givenValidTicketDTO_reserveATicket() {
        int sizeBeforeReservation = ticketsService.findAll(0, 5).size();

        TicketsToReserveDTO ticketsDTO = TicketConstants.returnTicketDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<TicketsToReserveDTO> request = new HttpEntity<>(ticketsDTO, headers);

        ResponseEntity<?> response = restTemplate.exchange(
                "/api/tickets/reserve", HttpMethod.POST, request, void.class
        );

        int sizeAfterReservation = ticketsService.findAll(0, 5).size();

        assertEquals(sizeBeforeReservation + 1, sizeAfterReservation);
    }

    @Test
    public void givenValidTicketId_cancelTicket() {
        int sizeBeforeCancellation = ticketsService.findAll(0, 5).size();

        //ticketsService.cancelTicket(TicketConstants.DELETE_TICKET_ID);

        ResponseEntity<?> response = restTemplate.exchange(
                "/api/tickets/cancel/" + TicketConstants.DELETE_TICKET_ID, HttpMethod.DELETE, createHttpEntity(), void.class
        );

        int sizeAfterCancellation = ticketsService.findAll(0, 5).size();

        assertEquals(sizeBeforeCancellation - 1, sizeAfterCancellation);
    }

    @Test
    public void whenBuyTicket_ticketNotFound() {
        ResponseEntity<ResourceNotFoundException> response = restTemplate.exchange(
                "/api/tickets/buy/" + TicketConstants.NON_EXISTING_ID, HttpMethod.GET, createHttpEntity(), ResourceNotFoundException.class
        );

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenBuyTicket_ticketAlreadyBought() {
        ResponseEntity<ApiRequestException> response = restTemplate.exchange(
                "/api/tickets/buy/" + TicketConstants.DB_ID_1, HttpMethod.GET, createHttpEntity(), ApiRequestException.class
        );

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void whenBuyTicket() {
        ResponseEntity<?> response = restTemplate.exchange(
                "/api/tickets/buy/" + TicketConstants.DB_ID_3, HttpMethod.GET, createHttpEntity(), void.class
        );

        assertNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
