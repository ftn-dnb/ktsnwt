package rs.ac.uns.ftn.ktsnwt.repository;


import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.TicketConstants;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TicketRepositoryIntegrationTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void givenValidPageAndSizeParameters_returnPagedTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>(ticketRepository.findAll(PageRequest.of(0, 5)).getContent());
        assertEquals(3, tickets.size());
    }

    @Test
    public void givenValidUserIdAndPageParameters_returnUsersTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>(ticketRepository.getByUserId(TicketConstants.DB_USER_ID_1, PageRequest.of(0, 5)).getContent());
        assertEquals(3, tickets.size());
    }

    @Test
    public void givenValidEventDayId_returnTicket() {
        Ticket ticket = ticketRepository.getByRowAndColumnAndEventDayId(4, 3, 3L);

        Long newTicketId = 3L;

        assertEquals(TicketConstants.NEW_DB_COLUMN_NUM, ticket.getColumn());
        assertEquals(TicketConstants.NEW_DB_EVENT_DAY_ID,  ticket.getEventDay().getId());
        assertEquals(TicketConstants.NEW_DB_PRICING_ID, ticket.getPricing().getId());
        assertEquals(TicketConstants.NEW_DB_ROW_NUM, ticket.getRow());
        assertEquals(TicketConstants.NEW_DB_DATE, ticket.getDatePurchased().toString());
        assertEquals(TicketConstants.NEW_DB_PURCHASED, ticket.isPurchased());
        assertEquals(TicketConstants.NEW_DB_USER_ID, ticket.getUser().getId());
        assertEquals(newTicketId, ticket.getId());
    }

    @Test
    public void givenValidPricingAndSectorIds_returnTicketsCount() {
        Long count = ticketRepository.getTicketsCountByPricingAndSector(3L, 2L);

        Long correctCount = 1L;

        assertEquals(correctCount, count);
    }
}
