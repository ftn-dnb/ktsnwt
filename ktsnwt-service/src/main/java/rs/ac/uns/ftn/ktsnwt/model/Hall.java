package rs.ac.uns.ftn.ktsnwt.model;

import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "halls")
public class Hall {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hall")
    private Set<Sector> sectors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Hall(){

    }

    public Hall(HallDTO hallDTO) {
        this.name = hallDTO.getName();
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

    public Set<Sector> getSectors() {
        if (this.sectors == null) {
            this.sectors = new HashSet<>();
        }
        return sectors;
    }

    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
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
        Hall hall = (Hall) obj;
        return id.equals(hall.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}


