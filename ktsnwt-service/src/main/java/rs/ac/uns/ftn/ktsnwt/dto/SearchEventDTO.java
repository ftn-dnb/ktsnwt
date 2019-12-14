package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import javax.validation.constraints.NotNull;

public class SearchEventDTO {

    @NotNull(message = "Start date must be provided for search")
    private String startDate;

    @NotNull(message = "End date must be provided for search")
    private String endDate;

    private EventType type;

    private Long location;

    public SearchEventDTO(){

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

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }
}
