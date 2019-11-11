package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Hall;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HallDTO {
    private Long id;
    private String name;
    private ArrayList<SectorDTO> sectors;
    private Long locationId;

    public HallDTO() {
        super();
    }

    public HallDTO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.locationId = hall.getLocation().getId();
        this.sectors = new ArrayList<>(
                hall.getSectors().stream().map(sector -> new SectorDTO(sector)).collect(Collectors.toList())
        );
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public ArrayList<SectorDTO> getSectors() {
        return sectors;
    }

    public void setSectors(ArrayList<SectorDTO> sectors) {
        this.sectors = sectors;
    }
}
