package rs.ac.uns.ftn.ktsnwt.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.common.TimeProvider;
import rs.ac.uns.ftn.ktsnwt.dto.*;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.mappers.EventMapper;
import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventStatus;
import rs.ac.uns.ftn.ktsnwt.repository.*;
import rs.ac.uns.ftn.ktsnwt.service.eventday.EventDayService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class EventServiceImpl implements EventService {

    @Autowired EventRepository eventRepository;
    @Autowired HallRepository hallRepository;
    @Autowired EventDayRepository eventDayRepository;
    @Autowired TimeProvider timeProvider;
    @Autowired EventMapper eventMapper;
    @Autowired SectorRepository sectorRepository;
    @Autowired PricingRepository pricingRepository;

    @Value("${user.default-profile-image}")
    private String defaultEventImage;

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
        e.setImagePath(defaultEventImage);
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

    @Override
    public Page<EventDTO> filterEvents(SearchEventDTO filter, Pageable pageable) {
        Date endDate;

        try{
            endDate = timeProvider.makeDate(filter.getEndDate());
        }
        catch (ParseException e){
            throw new ApiRequestException("Invalid date format");
        }

        return eventRepository.filterEvents(endDate,filter.getType(),filter.getLocation(), pageable).map(x -> eventMapper.toDTO(x));
    }

    @Override
    public Page<EventDTO> getAllEvents(Pageable pageable){
        return eventRepository.findAll(pageable).map(x -> eventMapper.toDTO(x));
    }

    @Override
    public void setNewEventImage(String path, Long id){
        Event e = eventRepository.findById(id).orElseThrow(() -> new ApiRequestException("Invalid id of event"));
        e.setImagePath(path);
        eventRepository.save(e);
    }

    @Override
    public Event editEvent(EventEditDTO event){
        Event e = eventRepository.findById(event.getId()).orElseThrow(() -> new ApiRequestException("Invalid id of event"));

        e.setPurchaseLimit(event.getPurchaseLimit());
        e.setTicketsPerUser(event.getTicketsPerUser());
        e.setDescription(event.getDescription());

        eventRepository.save(e);

        return e;
    }

    @Override
    public Event setEventPricing(Long eventId, List<SetSectorPriceDTO> pricing){
        Event e = eventRepository.findById(eventId).orElseThrow(() -> new ApiRequestException("Invalid id of event"));

        for (EventDay ed: e.getEventDays()) {
            Set<Pricing> realPricing = new HashSet<>();

            for (SetSectorPriceDTO sP: pricing) {
                Pricing p = new Pricing();
                p.setPrice(sP.getPrice());
                p.setSector(sectorRepository.findById(sP.getId()).orElseThrow(() -> new ApiRequestException("Invalid id of sector")));
                p.setEventDay(ed);
                pricingRepository.save(p);
                realPricing.add(p);
            }

            ed.setPricings(realPricing);
            eventDayRepository.save(ed);
        }

        return e;
    }
}