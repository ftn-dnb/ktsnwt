package rs.ac.uns.ftn.ktsnwt.mappers;

import org.springframework.data.domain.Page;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.model.Location;

import java.util.List;

import java.util.stream.Collectors;

public class LocationMapper {

    private LocationMapper() {
    }

    public static LocationDTO toDto(Location location) {
        return new LocationDTO(location);
    }

    public static List<LocationDTO> toListDto(List<Location> locations) {
        return locations.stream().map(LocationDTO::new).collect(Collectors.toList());
    }

    public static List<LocationDTO> toListDto(Page<Location> locations) {
        return locations.getContent().stream().map(LocationDTO::new).collect(Collectors.toList());
    }
}
