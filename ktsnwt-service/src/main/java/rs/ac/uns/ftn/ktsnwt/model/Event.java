package rs.ac.uns.ftn.ktsnwt.model;

import rs.ac.uns.ftn.ktsnwt.model.enums.EventType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="events")
public class Event {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="purchase_limit")
    private int purchaseLimit;

    @Column(name="tickets_per_user")
    private int ticketsPerUser;

    @Column(name="description")
    private String description;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="type")
    private EventType type;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_days_connection",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_day_id", referencedColumnName = "id"))
    private Set<EventDay> eventDays;



}
