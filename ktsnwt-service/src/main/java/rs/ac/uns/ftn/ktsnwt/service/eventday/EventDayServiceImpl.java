package rs.ac.uns.ftn.ktsnwt.service.eventday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.EventDayDescriptionEditDTO;
import rs.ac.uns.ftn.ktsnwt.exception.EventDayNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventStatus;
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

    @Override
    public void disableEventDay(Long id) {
        EventDay ed = eventDayRepository.findById(id).orElseThrow(()->  new EventDayNotFoundException("Invalid id of event day"));

        ed.setStatus(EventStatus.CANCELED);
        eventDayRepository.save(ed);
    }

    @Override
    public EventDay changeDescription(EventDayDescriptionEditDTO dto) {
        EventDay ed = eventDayRepository.findById(dto.getId()).orElseThrow(()->  new EventDayNotFoundException("Invalid id of event day"));

        ed.setDescription(dto.getDescription());
        eventDayRepository.save(ed);
        return ed;
    }


}
