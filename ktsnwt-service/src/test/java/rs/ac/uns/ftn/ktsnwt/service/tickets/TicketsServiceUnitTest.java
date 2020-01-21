package rs.ac.uns.ftn.ktsnwt.service.tickets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.common.TimeProvider;
import rs.ac.uns.ftn.ktsnwt.constants.*;
import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.repository.TicketRepository;
import rs.ac.uns.ftn.ktsnwt.service.eventday.EventDayService;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:application.properties")
//@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
public class TicketsServiceUnitTest {

    @Autowired
    TicketsService ticketsService;

    @MockBean
    TicketRepository ticketRepositoryMocked;

    @MockBean
    EventDayService eventDayServiceMocked;

    @MockBean
    PricingService pricingServiceMocked;

    @SpyBean
    TimeProvider timeProviderSpy;

    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        Mockito.when(ticketRepositoryMocked.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        ArrayList<Ticket> tickets = new ArrayList<>();
        Ticket t1 = new Ticket(); t1.setId(2L);
        Ticket t2 = new Ticket(); t1.setId(3L);
        tickets.add(t1);
        tickets.add(t2);
        Mockito.when(ticketRepositoryMocked.findAll(PageRequest.of(0,2))).thenReturn(new PageImpl<>(tickets));


        ArrayList<Ticket> ticketsReport = new ArrayList<>(TicketConstants.returnMockedTickets());

        //SecurityContext logged user

        User user = UserConstants.returnLoggedUser();
        SecurityContext securityContext = SecurityContextConstants.returnMockedSecurityContext();
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(ticketRepositoryMocked.getByUserId(user.getId(), PageRequest.of(0, 2))).thenReturn(new PageImpl<>(ticketsReport));

        //Reports
        Mockito.when(ticketRepositoryMocked.findAll()).thenReturn(ticketsReport);

    }

    @Test
    public void whenValidId_thenTicketShouldBeFound() {
        Long id = 1L;
        Ticket foundTicket = ticketsService.findById(id);

        assertEquals(id, foundTicket.getId());
    }

    @Test(expected = ApiRequestException.class)
    public void whenInvalidId_thenThrowEx() {
        Long id = 3L;
        Ticket foundTicket = ticketsService.findById(id);
    }

    @Test
    public void whenValidPage_thenReturnTickets() {
        int page = 0;
        int size = 2;
        List<Ticket> tickets = ticketsService.findAll(page, size);
        assertEquals(size, tickets.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidPage_thenThrowEx() {
        int page = -2;
        int size = 2;
        List<Ticket> tickets = ticketsService.findAll(page, size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidPageSize_thenThrowEx() {
        int page = 2;
        int size = -2;
        List<Ticket> tickets = ticketsService.findAll(page, size);
    }

    @Test
    //@WithMockUser(username = "jane.doe")
    public void whenUserLoggedInReturnHisTickets() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals("jane.doe", user.getUsername());

        List<Ticket> userTickets = ticketsService.getUsersTickets(0, 2);
        assertEquals(2, userTickets.size());
    }

    @Test
    public void whenDateStringValid_returnValidReportInfo() {
        Long locationId = 1L;
        ReportInfoDTO report = ticketsService.onLocationDailyReport(locationId, "2019-11-28");
        assertEquals(2, report.getTicketsSold());
        assertEquals(2400.0, report.getIncome(), 0.05);
    }

    @Test
    public void whenDateStringInvalid_returnInvalidReportInfo() {
        Long locationId = 1L;
        ReportInfoDTO report = ticketsService.onLocationDailyReport(locationId, "2019");
        assertEquals(-1, report.getTicketsSold());
        assertEquals(-1, report.getIncome(), 0.05);
    }

    @Test
    public void whenDateStringValid_returnValidReportInfoMonthly() {
        Long locationId = 1L;
        ReportInfoDTO report = ticketsService.onLocationMonthlyReport(locationId, "2019-11");
        assertEquals(2, report.getTicketsSold());
        assertEquals(2400.0, report.getIncome(), 0.05);
    }

    @Test
    public void whenDateStringInvalid_returnInvalidReportInfoMonthly() {
        Long locationId = 1L;
        ReportInfoDTO report = ticketsService.onLocationMonthlyReport(locationId, "2019");
        assertEquals(-1, report.getTicketsSold());
        assertEquals(-1, report.getIncome(), 0.05);
    }

    @Test
    public void whenDateStringValid_returnValidReportInfoForEvent() {
        Long eventId = 1L;
        ReportInfoDTO report = ticketsService.onEventDailyReport(eventId, "2019-11-28");
        assertEquals(2, report.getTicketsSold());
        assertEquals(2400.0, report.getIncome(), 0.05);
    }

    @Test
    public void whenDateStringInvalid_returnInvalidReportInfoForEvent() {
        Long eventId = 1L;
        ReportInfoDTO report = ticketsService.onLocationMonthlyReport(eventId, "2019");
        assertEquals(-1, report.getTicketsSold());
        assertEquals(-1, report.getIncome(), 0.05);
    }


    @Test(expected = ApiRequestException.class)
    public void whenEventDayNotActive_throwException() {
        TicketsToReserveDTO mockedDTO = TicketConstants.returnTicketCanceledEvent();
        Mockito.when(eventDayServiceMocked.getEventDay(mockedDTO.getEventDayId())).thenReturn(EventDayConstants.returnMockedCanceledEventDay());
        ticketsService.reserveTickets(mockedDTO);
    }


    @Test(expected = ApiRequestException.class)
    public void whenNumberTicketsTooLarge_throwException() {
        TicketsToReserveDTO mockedDTO = TicketConstants.returnTicketMuchSeats();
        Mockito.when(eventDayServiceMocked.getEventDay(mockedDTO.getEventDayId())).thenReturn(EventDayConstants.returnMockedEventDayEventSeatsTooMuch());
        ticketsService.reserveTickets(mockedDTO);
    }

    @Test(expected = ApiRequestException.class)
    public void whenPurchaseDateViolated_throwException() {
        try {
            Mockito.doReturn(new Timestamp(
                    new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-28").getTime()
            )).when(timeProviderSpy).nowTimestamp();
            TicketsToReserveDTO mockedDTO = TicketConstants.returnTicketPurchaseDay();
            Mockito.when(eventDayServiceMocked.getEventDay(mockedDTO.getEventDayId())).thenReturn(EventDayConstants.returnMockedEventDayEventPurchaseDate());
            ticketsService.reserveTickets(mockedDTO);
        } catch (ParseException e) {}

    }

    @Test
    public void givenValidData_reserveTicket() {
        TicketsToReserveDTO mockedDTO = TicketConstants.returnValidTicketDTO();
        Mockito.when(pricingServiceMocked.getPricing(PricingConstants.MOCK_ID_RESERVATION)).thenReturn(PricingConstants.returnReservationPricing());
        try {
            Mockito.when(eventDayServiceMocked.getEventDay(mockedDTO.getEventDayId())).thenReturn(EventDayConstants.returnMockedEventDayValid());
            Mockito.doReturn(new Timestamp(
                    new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-28").getTime()
            )).when(timeProviderSpy).nowTimestamp();
        } catch (ParseException e) {}
        Mockito.when(ticketRepositoryMocked.getTicketsCountByPricingAndSector(PricingConstants.MOCK_ID_RESERVATION, SectorConstants.MOCK_ID_RES)).thenReturn(7L);
        Mockito.when(ticketRepositoryMocked.getByRowAndColumnAndEventDayId(TicketConstants.MOCK_ROW_1, TicketConstants.MOCK_SEAT_1, mockedDTO.getEventDayId())).thenReturn(null);
        Mockito.when(ticketRepositoryMocked.getByRowAndColumnAndEventDayId(TicketConstants.MOCK_ROW_2, TicketConstants.MOCK_SEAT_2, mockedDTO.getEventDayId())).thenReturn(null);
        Mockito.when(ticketRepositoryMocked.save(any(Ticket.class))).thenReturn(null);

        ticketsService.reserveTickets(mockedDTO);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void givenInvalidTicketId_whenCancelTicket_throwEx() {
        Ticket invalidTicket = new Ticket();
        Ticket nullTicket = null;
        invalidTicket.setId(5L);
        Mockito.when(ticketRepositoryMocked.findById(invalidTicket.getId())).thenReturn(Optional.ofNullable(nullTicket));
        ticketsService.cancelTicket(invalidTicket.getId());
    }

    @Test(expected = ApiRequestException.class)
    public void givenInvalidTicketUser_whenCancelTicket_throwEx() {
        Ticket ticket = TicketConstants.returnTicketWithLoggedOffUser();
        Mockito.when(ticketRepositoryMocked.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        ticketsService.cancelTicket(ticket.getId());
    }

    @Test(expected = ApiRequestException.class)
    public void givenPurchasedTicket_whenCancelTicket_throwEx() {
        Ticket ticket = TicketConstants.returnPurchasedTicket();
        ticket.setUser((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Mockito.when(ticketRepositoryMocked.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        ticketsService.cancelTicket(ticket.getId());
    }

    @Test(expected = ApiRequestException.class)
    public void givenTicketWithInvalidDate_whenCancelTicket_throwEx() {
        try {
            Ticket ticket = TicketConstants.returnCancelTicketPurchaseLimit();
            ticket.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            Mockito.doReturn(
                    new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-24").getTime())
            ).when(timeProviderSpy).nowTimestamp();

            Mockito.when(ticketRepositoryMocked.findById(ticket.getId())).thenReturn(Optional.of(ticket));
            ticketsService.cancelTicket(ticket.getId());
        } catch (ParseException e) {}
    }

    @Test
    public void givenTicketWithValidDate_whenCancelTicket_deleteTicket() {
        try {
            Ticket ticket = TicketConstants.returnCancelTicketPurchaseLimit();
            ticket.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            //Mockito.doReturn(new Timestamp(
            //        new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-19").getTime()
            //)).when(timeProviderSpy).nowTimestamp();
            Mockito.when(timeProviderSpy.nowTimestamp()).thenReturn(new Timestamp(
                    new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-19").getTime()
            ));
            Mockito.when(ticketRepositoryMocked.findById(ticket.getId())).thenReturn(Optional.of(ticket));
            ticketsService.cancelTicket(ticket.getId());
        } catch (ParseException e) {}
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenBuyTicket_ticketNotFound() {
        final Long ticketId = 1L;
        Mockito.when(ticketRepositoryMocked.findById(ticketId)).thenReturn(Optional.empty());
        ticketsService.buyTicket(ticketId);
    }

    @Test(expected = ApiRequestException.class)
    public void whenBuyTicket_ticketAlreadyBought() {
        final Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setPurchased(true);

        Mockito.when(ticketRepositoryMocked.findById(ticketId)).thenReturn(Optional.of(ticket));
        ticketsService.buyTicket(ticketId);
    }

    @Test(expected = ApiRequestException.class)
    public void whenBuyTicket_timeRunOut() {
        final Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setPurchased(false);

        EventDay eventDay = new EventDay();
        eventDay.setDate(new Timestamp(System.currentTimeMillis()));
        ticket.setEventDay(eventDay);

        Mockito.when(ticketRepositoryMocked.findById(ticketId)).thenReturn(Optional.of(ticket));
        ticketsService.buyTicket(ticketId);
    }

    @Test
    public void whenBuyTicket() {
        final Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setPurchased(false);

        EventDay eventDay = new EventDay();
        eventDay.setDate(new Timestamp(System.currentTimeMillis() + 10000000));
        ticket.setEventDay(eventDay);

        Mockito.when(ticketRepositoryMocked.findById(ticketId)).thenReturn(Optional.of(ticket));
        Mockito.when(ticketRepositoryMocked.save(any(Ticket.class))).thenReturn(ticket);

        Ticket purchasedTicket = ticketsService.buyTicket(ticketId);
        assertEquals(ticketId, purchasedTicket.getId());
        assertTrue(purchasedTicket.isPurchased());
    }
}
