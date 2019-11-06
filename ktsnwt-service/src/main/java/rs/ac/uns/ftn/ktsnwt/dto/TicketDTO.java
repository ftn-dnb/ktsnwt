package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.EventDay;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;
import java.sql.Timestamp;

public class TicketDTO {
    private Long id;
    private int row;
    private int seat;
    private boolean purchased;
    private Timestamp datePurchased;
    private Long eventDayId;
    private Long eventId;
    private SectorType sectorType;
    private String sectorName;
    private String hallName;
    private String locationName;
    private AddressDTO address;
    private double price;


    public TicketDTO() {
    }

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.row = ticket.getRow();
        this.seat = ticket.getColumn();
        this.purchased = ticket.isPurchased();
        this.datePurchased = ticket.getDatePurchased();
        this.eventDayId = ticket.getEventDay().getId();
        this.eventId = ticket.getEventDay().getEvent().getId();
        this.sectorType = ticket.getPricing().getSector().getType();
        this.sectorName = ticket.getPricing().getSector().getName();
        this.hallName = ticket.getPricing().getSector().getHall().getName();
        this.locationName = ticket.getPricing().getSector().getHall().getLocation().getName();
        this.address = new AddressDTO(ticket.getPricing().getSector().getHall().getLocation().getAddress());
        this.price = ticket.getPricing().getPrice();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public Timestamp getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Timestamp datePurchased) {
        this.datePurchased = datePurchased;
    }

    public Long getEventDayId() {
        return eventDayId;
    }

    public void setEventDayId(Long eventDayId) {
        this.eventDayId = eventDayId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public SectorType getSectorType() {
        return sectorType;
    }

    public void setSectorType(SectorType sectorType) {
        this.sectorType = sectorType;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
