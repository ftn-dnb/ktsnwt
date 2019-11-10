package rs.ac.uns.ftn.ktsnwt.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.repository.EventRepository;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EventServiceImpl implements EventService {

    @Autowired EventRepository eventRepository;
    @Autowired HallRepository hallRepository;
    @Override
    public Event addEvent(EventDTO event) {
        Timestamp startDate;
        Timestamp endDate;
        try{
            startDate = makeTimestamps(event.getStartDate());
            endDate = makeTimestamps(event.getEndDate());
            Long count = eventRepository.checkCapturedHall(startDate,endDate,event.getHallId());
            if(count > 0){
                return  null;// posalji poruku
            }

            Event e = new Event();
            // mapper
            e.setName(event.getName());
            e.setType(event.getType());
            e.setTicketsPerUser(event.getTicketsPerUser());
            e.setPurchaseLimit(event.getPurchaseLimit());
            e.setDescription(event.getDescription());
            e.setStartDate(startDate);
            e.setEndDate(endDate);
            Hall h = hallRepository.getById(event.getHallId());
            e.setHall(h);

            eventRepository.save(e);
            return e;
        }
        catch (ParseException e){
            // posalji poruku
        }

        //end date mora biti posle start date

        return null;
    }

    private Timestamp makeTimestamps(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date parseDate = dateFormat.parse(date);
        return new Timestamp(parseDate.getTime());
    }
}
