package rs.ac.uns.ftn.ktsnwt.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EventEditDTO {

    @NotNull(message = "Id of event must be provided")
    Long id;

    @NotNull(message = "Purchase limit must be provided")
    @Positive
    int purchaseLimit;

    @NotNull(message = "Number of tickets per user must be provided")
    @Positive
    int ticketsPerUser;

    @NotNull(message = "Description must be provided")
    String description;

    public EventEditDTO(){
    }

    public int getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(int purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }

    public int getTicketsPerUser() {
        return ticketsPerUser;
    }

    public void setTicketsPerUser(int ticketsPerUser) {
        this.ticketsPerUser = ticketsPerUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
