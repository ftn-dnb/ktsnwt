package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Event;

public class EventDetailedDTO {
    private Long id;
    private String name;

    public EventDetailedDTO(){

    }

    public EventDetailedDTO(Event e){
        this.id = e.getId();
        this.name = e.getName();
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
}
