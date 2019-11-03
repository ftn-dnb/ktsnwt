package rs.ac.uns.ftn.ktsnwt.service.tickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.common.TimeProvider;
import rs.ac.uns.ftn.ktsnwt.dto.PricingSeatDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.*;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;
import rs.ac.uns.ftn.ktsnwt.repository.TicketRepository;
import rs.ac.uns.ftn.ktsnwt.service.eventday.EventDayService;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingService;

@Service
public class TicketsServiceImpl implements TicketsService {

    @Autowired
    private EventDayService eventDayService;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TimeProvider timeProvider;


    @Override
    public void reserveTickets(TicketsToReserveDTO ticketsInfo) {
        EventDay eventDay = eventDayService.getEventDay(ticketsInfo.getEventDayId());
        Event event = eventDay.getEvent();

        if (ticketsInfo.getSeats().size() > event.getTicketsPerUser())
            throw new ApiRequestException("You can't buy more than " + event.getTicketsPerUser() + " tickets at once.");


        if (event.getStartDate().before(timeProvider.addDaysToDate(timeProvider.now(), event.getPurchaseLimit())))
            throw new ApiRequestException("You can't buy a ticket because purchase date has expired.");

        ticketsInfo.getSeats().stream().forEach(seat-> createTicket(seat, eventDay));
    }

    private void createTicket(PricingSeatDTO seat, EventDay eventDay) {
        Pricing pricing = pricingService.getPricing(seat.getPricingId());
        Sector sector = pricing.getSector();

        if (ticketRepository.getTicketsByPricingAndSector(pricing.getId(), sector.getId()) + 1 > sector.getCapacity())
            throw new ApiRequestException("Sector with id " + sector.getId() + " doesn't have enough space.");

        if (sector.getType() == SectorType.FLOOR) {
            saveTicket(0, 0, pricing, eventDay);
        } else { // SectoryType.SEATS
            if (isSeatFree(eventDay, sector, seat)) {
                saveTicket(seat.getRow(), seat.getSeat(), pricing, eventDay);
            }
        }
    }

    private boolean isSeatFree(EventDay eventDay, Sector sector, PricingSeatDTO seat) {
        Ticket ticket = ticketRepository.getByRowAndColumnAndEventDayId(seat.getRow(), seat.getSeat(), eventDay.getId());
        return ticket == null;
    }

    private void saveTicket(int row, int seat, Pricing pricing, EventDay eventDay) {
        Ticket ticket = new Ticket();
        ticket.setDeleted(false);
        ticket.setPurchased(false);
        ticket.setRow(row);
        ticket.setColumn(seat);
        ticket.setPricing(pricing);
        ticket.setEventDay(eventDay);

        ticketRepository.save(ticket);
    }
}
