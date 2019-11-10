package rs.ac.uns.ftn.ktsnwt.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.common.TimeProvider;
import rs.ac.uns.ftn.ktsnwt.dto.EventDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.mappers.EventMapper;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventStatus;
import rs.ac.uns.ftn.ktsnwt.repository.EventDayRepository;
import rs.ac.uns.ftn.ktsnwt.repository.EventRepository;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class EventServiceImpl implements EventService {

    @Autowired EventRepository eventRepository;
    @Autowired HallRepository hallRepository;
    @Autowired EventDayRepository eventDayRepository;
    @Autowired TimeProvider timeProvider;
    @Override
    public Event addEvent(EventDTO event) {
        Date startDate;
        Date endDate;
        try{
            startDate = timeProvider.makeDate(event.getStartDate());
            endDate = timeProvider.makeDate(event.getEndDate());
        }
        catch (ParseException e){
            throw new ApiRequestException("Invalid date format");
        }

        if(endDate.before(startDate)){
            throw new ApiRequestException("End date is after start date");
        }

        Long count = eventRepository.checkCapturedHall(startDate,endDate,event.getHallId());

        if(count > 0){
            throw new ApiRequestException("Hall " + event.getHallId() +  " is occupied");
        }

        Event e = EventMapper.toEntity(event);
        e.setStartDate(new Timestamp(startDate.getTime()));
        e.setEndDate(new Timestamp(endDate.getTime()));
        Hall h = hallRepository.getById(event.getHallId());
        e.setHall(h);
        eventRepository.save(e);
        e.setEventDays(makeBasicEventDay(startDate,endDate,e));

        return e;
    }

    private Set<EventDay> makeBasicEventDay(Date startDate, Date endDate, Event e){
        Set<EventDay> eventDays = new HashSet<>();
        Long diff = TimeUnit.DAYS.convert(Math.abs(startDate.getTime() - endDate.getTime()), TimeUnit.MILLISECONDS);
        Date date = new Date(startDate.getTime());
        for(int i = 0; i<=diff; i++ ){
            if(i > 0){
                date = new Date(date.getTime() + 86400000);
            }
            EventDay ed = new EventDay();
            ed.setEvent(e);
            ed.setName("Day " + (i+1));
            ed.setStatus(EventStatus.ACTIVE);
            ed.setDate(new Timestamp(date.getTime()));
            ed.setDescription("ADD DESCRIPTION");
            eventDayRepository.save(ed);
            eventDays.add(ed);
        }
        return eventDays;
    }
}
