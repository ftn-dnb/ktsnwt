package rs.ac.uns.ftn.ktsnwt.service.tickets;

import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.dto.TicketsToReserveDTO;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import java.util.List;

public interface TicketsService {
    Ticket findById(Long id);
    List<Ticket> findAll(int page, int size);
    List<Ticket> getTicketsByEventDayId(Long id);
    ReportInfoDTO onLocationDailyReport(long idLocation, String date);
    ReportInfoDTO onLocationMonthlyReport(long idLocation, String date);
    ReportInfoDTO onEventDailyReport(long idEvent, String date);
    List<Ticket> getUsersTickets(int page, int size);
    void reserveTickets(TicketsToReserveDTO ticketsInfo);
    void cancelTicket(Long id);
    Ticket buyTicket(Long id);
}
