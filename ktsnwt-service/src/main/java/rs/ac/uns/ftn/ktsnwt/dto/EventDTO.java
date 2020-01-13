package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Event;
import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import javax.validation.constraints.NotNull;

public class EventDTO {

    private Long id;

    @NotNull(message = "Name of event must be provided")
    private String name;

    @NotNull(message = "Start date of event must be provided")
    private String startDate;

    @NotNull(message = "End date of event must be provided")
    private String endDate;

    @NotNull(message = "Purchase limit of event must be provided")
    private Integer purchaseLimit;

    @NotNull(message = "Purchase limit of event must be provided")
    private Integer ticketsPerUser;

    private String description;

    private String imagePath;

    @NotNull(message = "Type of event must be provided")
    private EventType type;

    @NotNull(message = "ID of hall must be provided")
    private Long hallId;

    public EventDTO(){

    }

    public EventDTO(Event event){
        this.id = event.getId();
        this.name = event.getName();
        this.purchaseLimit = event.getPurchaseLimit();
        this.ticketsPerUser = event.getTicketsPerUser();
        this.description = event.getDescription();
        this.imagePath = event.getImagePath();
        this.startDate = event.getStartDate().toString();
        this.endDate = event.getEndDate().toString();
        this.type = event.getType();
        this.hallId = event.getHall().getId();
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

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
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

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
