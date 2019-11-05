package rs.ac.uns.ftn.ktsnwt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.AddressMapper;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.service.address.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        Address address = addressService.findById(id);
        return new ResponseEntity<>(AddressMapper.toDto(address), HttpStatus.OK);
    }

    @GetMapping("/googleApi/{id}")
    public ResponseEntity<AddressDTO> getAddressByGoogleApiId(@PathVariable String id) {
        Address address = addressService.findByGoogleApiId(id);
        return new ResponseEntity<>(AddressMapper.toDto(address), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        Address address = addressService.addAddress(addressDTO);
        return new ResponseEntity<>(AddressMapper.toDto(address), HttpStatus.OK);
    }
}
