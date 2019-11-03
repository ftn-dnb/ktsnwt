package rs.ac.uns.ftn.ktsnwt.model;

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

    @OneToMany
    @JoinTable(name = "hall_sectors",
            joinColumns = @JoinColumn (name = "hall_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id", referencedColumnName = "id"))
    private Set<Sector> sectors = new HashSet<>();

    public Hall(){

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
        return sectors;
    }

    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
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
