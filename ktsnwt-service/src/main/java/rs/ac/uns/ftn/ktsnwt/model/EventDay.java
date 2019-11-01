package rs.ac.uns.ftn.ktsnwt.model;

import rs.ac.uns.ftn.ktsnwt.model.enums.EventStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event_days")
public class EventDay {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private EventStatus status;

    @OneToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;
}
