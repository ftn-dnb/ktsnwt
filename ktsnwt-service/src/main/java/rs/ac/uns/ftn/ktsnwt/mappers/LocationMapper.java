package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.model.Location;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LocationMapper {

    private LocationMapper() {

    }

    public static LocationDTO toDto(Location location) {
        return new LocationDTO(location);
    }
}
