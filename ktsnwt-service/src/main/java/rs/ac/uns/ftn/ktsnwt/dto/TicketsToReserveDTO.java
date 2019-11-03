package rs.ac.uns.ftn.ktsnwt.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class TicketsToReserveDTO {

    @NotNull(message = "Event-day ID is required")
    private Long eventDayId;
    private List<PricingSeatDTO> seats;

    public TicketsToReserveDTO() {
    }

    public Long getEventDayId() {
        return eventDayId;
    }

    public void setEventDayId(Long eventDayId) {
        this.eventDayId = eventDayId;
    }

    public List<PricingSeatDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<PricingSeatDTO> seats) {
        this.seats = seats;
    }
}

