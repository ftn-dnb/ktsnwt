package rs.ac.uns.ftn.ktsnwt.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "locations")
public class Location {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "string", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "location_events",
            joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private Set<Event> events = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "location_halls",
            joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hall_id", referencedColumnName = "id"))
    private Set<Hall> halls = new HashSet<>();

    public Location(){

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Hall> getHalls() {
        return halls;
    }

    public void setHalls(Set<Hall> halls) {
        this.halls = halls;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return  false;
        Location location = (Location) obj;
        return id.equals(location.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
