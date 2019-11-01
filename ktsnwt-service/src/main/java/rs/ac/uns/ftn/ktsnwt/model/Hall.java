package rs.ac.uns.ftn.ktsnwt.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "halls")
public class Hall {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(name = "hall_sectors",
            joinColumns = @JoinColumn (name = "hall_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id", referencedColumnName = "id"))
    private Set<Sector> sectors;
}
