package rs.ac.uns.ftn.ktsnwt.model;

import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sectors")
public class Sector {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "numRows")
    private int numRows;

    @Column(name = "numColumns")
    private int numColumns;

    @Column(name ="capacity", nullable = false)
    private int capacity;

    @Column(name = "type", nullable = false)
    private SectorType type;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    public Sector(){

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

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public SectorType getType() {
        return type;
    }

    public void setType(SectorType type) {
        this.type = type;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return  false;
        Sector sector = (Sector) obj;
        return id.equals(sector.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
