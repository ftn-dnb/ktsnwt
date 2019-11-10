package rs.ac.uns.ftn.ktsnwt.model;

import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;

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

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "location")
    private Set<Hall> halls = new HashSet<>();

    public Location(){

    }

    public Location(LocationDTO locationDTO) {
        this.name = locationDTO.getName();
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

    public Set<Hall> getHalls() {
        if (this.halls == null) {
            this.halls = new HashSet<>();
        }
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
