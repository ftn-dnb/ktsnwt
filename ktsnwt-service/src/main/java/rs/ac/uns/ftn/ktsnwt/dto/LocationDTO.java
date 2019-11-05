package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.mappers.AddressMapper;
import rs.ac.uns.ftn.ktsnwt.model.Location;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LocationDTO {

    private Long id;
    private String name;
    private AddressDTO address;
    //private ArrayList<EventDTO> events;
    private ArrayList<HallDTO> halls;

    public LocationDTO() {
        super();
    }

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.address = AddressMapper.toDto(location.getAddress());
        this.halls = new ArrayList(
                location.getHalls().stream().map(hall -> new HallDTO(hall)).collect(Collectors.toList())
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public ArrayList<HallDTO> getHalls() {
        return halls;
    }

    public void setHalls(ArrayList<HallDTO> halls) {
        this.halls = halls;
    }
}
