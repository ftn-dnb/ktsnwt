package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Hall;

public class HallDTO {
    private Long id;
    private String name;
    //private ArrayList<SectorDTO> sectors;
    private Long locationId;

    public HallDTO() {
        super();
    }

    public HallDTO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.locationId = hall.getLocation().getId();
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
}
