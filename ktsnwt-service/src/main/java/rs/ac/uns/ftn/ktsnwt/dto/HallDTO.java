package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Hall;

public class HallDTO {
    private Long id;
    private String name;
    //private ArrayList<SectorDTO> sectors;
    private LocationDTO location;

    public HallDTO() {
        super();
    }

    public HallDTO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }
}
