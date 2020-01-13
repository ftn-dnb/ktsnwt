package rs.ac.uns.ftn.ktsnwt.service.location;

import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.model.Location;

import java.util.List;


public interface LocationService {
    List<Location> findAll();
    Location findById(Long id);
    List<Location> findAll(int page, int size);
    Location findByName(String name);
    Location addLocation(LocationDTO locationDTO);
    Location editLocation(LocationDTO locationDTO);
    Location changeAddress(Long id, AddressDTO addressDTO);
}
