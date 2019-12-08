package rs.ac.uns.ftn.ktsnwt.service.eventday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.exception.EventDayNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.repository.EventDayRepository;

@Service
public class EventDayServiceImpl implements EventDayService {

    @Autowired
    private EventDayRepository eventDayRepository;

    @Override
    public EventDay getEventDay(Long id) {
        EventDay eventDay = eventDayRepository.findById(id).orElse(null);

        if (eventDay == null)
            throw new EventDayNotFoundException("Event day with id " + id + " doesn't exist.");

        return eventDay;
    }
}
