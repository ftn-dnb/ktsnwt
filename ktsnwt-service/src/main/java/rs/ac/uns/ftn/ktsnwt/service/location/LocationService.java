package rs.ac.uns.ftn.ktsnwt.service.location;

import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.model.Location;

public interface LocationService {

    Location findById(Long id);
    Location findByName(String name);
    Location addLocation(LocationDTO locationDTO);
    Location editLocation(LocationDTO locationDTO);
    Location changeAddress(Long id, AddressDTO addressDTO);
}
