package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import javax.validation.constraints.NotNull;

public class SearchEventDTO {


    @NotNull(message = "End date must be provided for search")
    private String endDate;

    private EventType type;

    private String location;

    private String name;

    public SearchEventDTO(){

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
