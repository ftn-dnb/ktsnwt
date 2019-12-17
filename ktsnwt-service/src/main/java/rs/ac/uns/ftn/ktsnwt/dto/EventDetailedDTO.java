package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;

public class EventDetailedDTO {
    private Long id;
    private String name;
    private EventDayDTO oneDay;


    public EventDetailedDTO(){

    }

    public EventDetailedDTO(Event e){
        this.id = e.getId();
        this.name = e.getName();
        this.oneDay = new EventDayDTO(e.getEventDays().iterator().next());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventDayDTO getOneDay() {
        return oneDay;
    }

    public void setOneDay(EventDayDTO oneDay) {
        this.oneDay = oneDay;
    }
}
