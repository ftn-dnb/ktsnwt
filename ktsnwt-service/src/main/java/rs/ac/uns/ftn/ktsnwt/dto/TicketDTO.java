package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;

public class TicketDTO {
    private Long id;
    private int row;
    private int column;
    private boolean purchased;
    private boolean deleted;
    private Pricing pricing;
    private EventDay eventDay;

    public TicketDTO() {
    }

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.row = ticket.getRow();
        this.column = ticket.getColumn();
        this.purchased = ticket.isPurchased();
        this.deleted = ticket.isDeleted();
        this.pricing = ticket.getPricing();
        this.eventDay = ticket.getEventDay();
    }


    public TicketDTO(Long id, int row, int column, boolean purchased, boolean deleted, Pricing pricing, EventDay eventDay) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.purchased = purchased;
        this.deleted = deleted;
        this.pricing = pricing;
        this.eventDay = eventDay;
    }

    public Long getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public EventDay getEventDay() {
        return eventDay;
    }

    public void setEventDay(EventDay eventDay) {
        this.eventDay = eventDay;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Pricing getPricing() {
        return pricing;
    }
}
