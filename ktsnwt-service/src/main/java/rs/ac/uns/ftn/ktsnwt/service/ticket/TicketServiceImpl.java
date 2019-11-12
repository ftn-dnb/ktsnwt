package rs.ac.uns.ftn.ktsnwt.service.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.ReportInfoDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.repository.TicketRepository;
import rs.ac.uns.ftn.ktsnwt.service.location.LocationService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private LocationService locationService;


    @Override
    public Ticket findById(Long id) {
        try {
            return ticketRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ApiRequestException("Ticket with id '" + id + "' doesn't exist.");
        }
    }

    @Override
    public List<Ticket> findAll(int page) {
        return ticketRepository.findAll(PageRequest.of(page, 5)).toList();
    }

    @Override
    public ReportInfoDTO onLocationDailyReport(long idLocation, String date) {
        return null;
    }

    @Override
    public ReportInfoDTO onLocationMonthlyReport(long idLocation) {
        return null;
    }

    @Override
    public ReportInfoDTO onEventDailyReport(long idEvent, String date) {
        return null;
    }
}
