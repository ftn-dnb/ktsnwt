package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;

public class SectorDTO {

    private Long id;
    private String name;
    private int numRows;
    private int numColumns;
    private int capacity;
    private SectorType type;
    private Long hallId;

    public SectorDTO() {
        super();
    }

    public SectorDTO(Sector sector) {
        this.id = sector.getId();
        this.name = sector.getName();
        this.numColumns = sector.getNumColumns();
        this.numRows = sector.getNumRows();
        this.capacity = sector.getCapacity();
        this.type = sector.getType();
        this.hallId = sector.getHall().getId();
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

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }
}
