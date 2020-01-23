package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import java.util.List;
import java.util.stream.Collectors;

public class EventAllDetailsDTO {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private Integer purchaseLimit;
    private Integer ticketsPerUser;
    private String description;
    private String imagePath;
    private EventType type;
    private HallDTO hall;
    private List<EventDayDTO> days;
    private List<PricingDTO> pricing;
    private LocationDTO location;

    public EventAllDetailsDTO(){

    }

    public EventAllDetailsDTO(Event e){
        this.id = e.getId();
        this.name = e.getName();
        this.startDate = e.getStartDate().toString();
        this.endDate = e.getEndDate().toString();
        this.purchaseLimit = e.getPurchaseLimit();
        this.ticketsPerUser = e.getTicketsPerUser();
        this.description = e.getDescription();
        this.imagePath = e.getImagePath();
        this.type = e.getType();
        this.hall  = new HallDTO(e.getHall());
        this.location = new LocationDTO(e.getHall().getLocation());
        this.days = e.getEventDays().stream().map(EventDayDTO::new).collect(Collectors.toList());
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(Integer purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }

    public Integer getTicketsPerUser() {
        return ticketsPerUser;
    }

    public void setTicketsPerUser(Integer ticketsPerUser) {
        this.ticketsPerUser = ticketsPerUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public List<EventDayDTO> getDays() {
        return days;
    }

    public void setDays(List<EventDayDTO> days) {
        this.days = days;
    }

    public List<PricingDTO> getPricing() {
        return pricing;
    }

    public void setPricing(List<PricingDTO> pricing) {
        this.pricing = pricing;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public HallDTO getHall() {
        return hall;
    }

    public void setHall(HallDTO hall) {
        this.hall = hall;
    }
}

