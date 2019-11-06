package rs.ac.uns.ftn.ktsnwt.model;

import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="events")
public class Event {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="start_date", nullable = false)
    private Date startDate;

    @Column(name="end_date", nullable = false)
    private Date endDate;

    @Column(name="purchase_limit", nullable = false)
    private int purchaseLimit;

    @Column(name="tickets_per_user", nullable = false)
    private int ticketsPerUser;

    @Column(name="description")
    private String description;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="type", nullable = false)
    private EventType type;


    @ManyToOne
    @JoinColumn(name = "location")
    private Location location;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_days_connection",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_day_id", referencedColumnName = "id"))
    private Set<EventDay> eventDays = new HashSet<>();

    public Event(){

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Set<EventDay> getEventDays() {
        return eventDays;
    }

    public void setEventDays(Set<EventDay> eventDays) {
        this.eventDays = eventDays;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return  false;
        Event event = (Event) obj;
        return id.equals(event.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
