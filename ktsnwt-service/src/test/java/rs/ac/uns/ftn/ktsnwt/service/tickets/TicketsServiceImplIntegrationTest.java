package rs.ac.uns.ftn.ktsnwt.service.tickets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.common.TimeProvider;
import rs.ac.uns.ftn.ktsnwt.constants.LocationConstants;
import rs.ac.uns.ftn.ktsnwt.constants.TicketConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.repository.TicketRepository;
import rs.ac.uns.ftn.ktsnwt.service.eventday.EventDayService;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingService;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketsServiceImplIntegrationTest {

    @Autowired
    private TicketsServiceImpl ticketsService;

    @Autowired
    private EventDayService eventDayService;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AuthenticationManager authManager;

    @Before
    public void setUp() {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(UserConstants.DB_USER_USERNAME, UserConstants.DB_USER_PASSWORD);
        Authentication auth = authManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
    }

    @Test
    public void givenValidTicketId_returnTicket() {
        Ticket ticket = ticketsService.findById(TicketConstants.DB_ID_1);
        assertEquals(TicketConstants.DB_ID_1, ticket.getId());
    }

    @Test(expected = ApiRequestException.class)
    public void givenInvalidTicketId_throwException() {
        Ticket ticket = ticketsService.findById(TicketConstants.NON_EXISTING_ID);

    }

    @Test
    public void givenValidPageAndSizeParameters_returnTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>(ticketRepository.findAll(PageRequest.of(0, 3)).getContent());
        assertEquals(3, tickets.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidPageAndSizeParameters_throwException() {
        ArrayList<Ticket> tickets = new ArrayList<>(ticketRepository.findAll(PageRequest.of(-1, 0)).getContent());
    }

    @Test
    public void givenValidLocationId_returnReport() {
        ReportInfoDTO reportInfo = ticketsService.onLocationDailyReport(LocationConstants.DB_ID, "2019-11-20");
        assertEquals(2, reportInfo.getTicketsSold());
        assertEquals(398, reportInfo.getIncome(), 0.05);
    }

    @Test
    public void givenValidLocationId_returnMonthlyReport() {
        ReportInfoDTO reportInfo = ticketsService.onLocationMonthlyReport(LocationConstants.DB_ID, "2019-11");
        assertEquals(2, reportInfo.getTicketsSold());
        assertEquals(398, reportInfo.getIncome(), 0.05);
    }

    @Test
    public void givenValidEventId_returnReport() {
        ReportInfoDTO reportInfo = ticketsService.onEventDailyReport(1L, "2019-11-20");
        assertEquals(2, reportInfo.getTicketsSold());
        assertEquals(398, reportInfo.getIncome(), 0.05);
    }

    @Test
    public void givenValidPageAndSizeParameters_returnUsersTickets() {
        Page<Ticket> tickets = ticketsService.getUsersTickets(0, 3);
        assertEquals(3, tickets.getContent().size());
    }

    @Test
    @Transactional @Rollback(true)
    public void givenValidTicketDTO_reserveATicket() {
        int sizeBeforeReservation = ticketsService.findAll(0, 5).size();

        TicketsToReserveDTO ticketsDTO = TicketConstants.returnTicketDTO();
        ticketsService.reserveTickets(ticketsDTO);

        int sizeAfterReservation = ticketsService.findAll(0, 5).size();

        assertEquals(sizeBeforeReservation + 1, sizeAfterReservation);
    }

    @Test
    @Transactional @Rollback(true)
    public void givenValidTicketId_cancelTicket() {
        int sizeBeforeCancellation = ticketsService.findAll(0, 5).size();

        ticketsService.cancelTicket(TicketConstants.DELETE_TICKET_ID);

        int sizeAfterCancellation = ticketsService.findAll(0, 5).size();

        assertEquals(sizeBeforeCancellation - 1, sizeAfterCancellation);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenBuyTicket_ticketNotFound() {
        ticketsService.buyTicket(TicketConstants.NON_EXISTING_ID);
    }

    @Test(expected = ApiRequestException.class)
    public void whenBuyTicket_ticketAlreadyBought() {
        ticketsService.buyTicket(TicketConstants.DB_ID_1);
    }

    @Test(expected = ApiRequestException.class)
    public void whenBuyTicket_timeRunOut() {
        ticketsService.buyTicket(TicketConstants.DB_ID_1);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenBuyTicket() {
        Ticket purchasedTicket = ticketsService.buyTicket(TicketConstants.DB_ID_4);
        assertEquals(TicketConstants.DB_ID_4, purchasedTicket.getId());
        assertTrue(purchasedTicket.isPurchased());
    }
}
