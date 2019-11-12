package rs.ac.uns.ftn.ktsnwt.service.ticket;

import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

import java.util.List;


public interface TicketService {
    Ticket findById(Long id);
    List<Ticket> findAll(int page);
    ReportInfoDTO onLocationDailyReport(long idLocation, String date);
    ReportInfoDTO onLocationMonthlyReport(long idLocation);
    ReportInfoDTO onEventDailyReport(long idEvent, String date);
}
