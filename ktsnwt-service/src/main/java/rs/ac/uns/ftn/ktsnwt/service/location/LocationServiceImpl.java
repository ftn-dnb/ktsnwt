package rs.ac.uns.ftn.ktsnwt.service.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepository;
import rs.ac.uns.ftn.ktsnwt.repository.LocationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Location> findAll() {
        try {
            return locationRepository.findAll();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(
                    "Locations not found"
            );
        }
    }

    @Override
    public Location findById(Long id) {
        try {
            return locationRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(
                    "Location not found"
            );
        }
    }

    @Override
    public Location findByName(String name) {
        try {
            return locationRepository.findByName(name);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(
                    "Location not found"
            );
        }
    }

    @Override
    public Location addLocation(LocationDTO locationDTO) {
        if (locationRepository.findByName(locationDTO.getName()) != null) {
            throw new ApiRequestException("Location with that name already exists.");
        }

        Address address;

        if (addressRepository.findByGoogleApiId(locationDTO.getAddress().getGoogleApiId()) == null) {
            address = new Address(locationDTO.getAddress());
            addressRepository.save(address);
        } else {
            address = addressRepository.findByGoogleApiId(locationDTO.getAddress().getGoogleApiId());
        }

        Location location = new Location(locationDTO);
        location.setAddress(address);
        locationRepository.save(location);

        return location;
    }

    @Override
    public Location editLocation(LocationDTO locationDTO) {
        if (locationRepository.findByName(locationDTO.getName()) != null) {
            throw new ApiRequestException("Location with that name already exists.");
        }

        Location location = locationRepository.findById(locationDTO.getId()).get();
        location.setName(locationDTO.getName());

        locationRepository.save(location);

        return location;
    }

    @Override
    public Location changeAddress(Long id, AddressDTO addressDTO) {
        Address address;

        if (addressRepository.findByGoogleApiId(addressDTO.getGoogleApiId()) == null) {
            address = new Address(addressDTO);
            addressRepository.save(address);
        } else {
            address = addressRepository.findByGoogleApiId(addressDTO.getGoogleApiId());
        }

        Location location = locationRepository.findById(id).get();
        location.setAddress(address);
        locationRepository.save(location);

        return location;
    }
}
