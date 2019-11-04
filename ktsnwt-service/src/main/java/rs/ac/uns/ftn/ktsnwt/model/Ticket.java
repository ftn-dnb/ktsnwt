package rs.ac.uns.ftn.ktsnwt.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_seat")
    private int row;

    @Column(name = "seat")
    private int column;

    @Column(name = "purchased", nullable = false)
    private boolean purchased;

    @Column(name = "date_purchased")
    private Timestamp datePurchased;

    @ManyToOne
    @JoinColumn(name = "event_day_id", referencedColumnName = "id", nullable = false)
    private EventDay eventDay;

    @ManyToOne
    @JoinColumn(name = "pricing_id", referencedColumnName = "id", nullable = false)
    private Pricing pricing;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Ticket(){

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

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public EventDay getEventDay() {
        return eventDay;
    }

    public void setEventDay(EventDay eventDay) {
        this.eventDay = eventDay;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public Timestamp getDatePurchased() {
        return datePurchased;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDatePurchased(Timestamp datePurchased) {
        this.datePurchased = datePurchased;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return  false;
        Ticket ticket = (Ticket) obj;
        return id.equals(ticket.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
