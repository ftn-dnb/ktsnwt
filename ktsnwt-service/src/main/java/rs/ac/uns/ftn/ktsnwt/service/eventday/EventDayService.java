package rs.ac.uns.ftn.ktsnwt.service.eventday;

import rs.ac.uns.ftn.ktsnwt.dto.EventDayDescriptionEditDTO;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;

public interface EventDayService {

    EventDay getEventDay(Long id);
    void disableEventDay(Long id);
    EventDay changeDescription(EventDayDescriptionEditDTO dto);
}
