package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EventDayDTO {

    private Long id;
    private String name;
    private String description;
    private Timestamp date;
    private EventStatus status;
    private Long eventId;

    private List<PricingDTO> pricing ;

    public EventDayDTO(){

    }

    public EventDayDTO(EventDay eD){
        this.id = eD.getId();
        this.name = eD.getName();
        this.description = eD.getDescription();
        this.date = eD.getDate();
        this.status = eD.getStatus();
        this.eventId = eD.getEvent().getId();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<PricingDTO> getPricing() {
        return pricing;
    }

    public void setPricing(List<PricingDTO> pricing) {
        this.pricing = pricing;
    }
}
