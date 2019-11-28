package rs.ac.uns.ftn.ktsnwt.service.tickets;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.SecurityContextConstants;
import rs.ac.uns.ftn.ktsnwt.constants.TicketConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.repository.TicketRepository;

import java.text.ParseException;
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

    @Before
    public void setUp() throws ParseException {
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
    @WithMockUser(username = "jane.doe")
    public void testshit() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals("jane.doe", user.getUsername());

        List<Ticket> userTickets = ticketsService.getUsersTickets(0, 2);
        assertEquals(2, userTickets.size());
    }


}
