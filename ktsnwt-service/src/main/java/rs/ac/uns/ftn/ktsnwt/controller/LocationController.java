package rs.ac.uns.ftn.ktsnwt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.LocationMapper;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.service.location.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        Location location = locationService.findById(id);
        return new ResponseEntity<>(new LocationDTO(location), HttpStatus.OK);
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<List<LocationDTO>> getAllLocations(@PathVariable int page) {
        List<Location> locations = locationService.findAll(page);
        return new ResponseEntity<>(LocationMapper.toListDto(locations), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<LocationDTO> getLocationByName(@PathVariable String name) {
        Location location = locationService.findByName(name);
        return new ResponseEntity<>(new LocationDTO(location), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationDTO locationDTO) {
        Location location = locationService.addLocation(locationDTO);
        return new ResponseEntity<>(new LocationDTO(location), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<LocationDTO> editLocation(@RequestBody LocationDTO locationDTO) {
        Location location = locationService.editLocation(locationDTO);
        return new ResponseEntity<>(new LocationDTO(location), HttpStatus.OK);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<LocationDTO> changeLocationAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        Location location = locationService.changeAddress(id, addressDTO);
        return new ResponseEntity<>(new LocationDTO(location), HttpStatus.OK);
    }
}
