package rs.ac.uns.ftn.ktsnwt.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepository;

import java.util.NoSuchElementException;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findById(Long id) {
        try {
            return addressRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(
                    "Address with id '" + id + "' doesn't exist."
            );
        }
    }

    @Override
    public Address findByGoogleApiId(String googleApiId) {
        Address address = addressRepository.findByGoogleApiId(googleApiId);
        if (address.getCity() == null) {
            throw new ResourceNotFoundException(
                    "Address with googleId: '" + googleApiId + "' doesn't exist."
            );
        }

        return address;
    }

    @Override
    public Address addAddress(AddressDTO addressDTO) {
        if (addressRepository.findByGoogleApiId(addressDTO.getGoogleApiId()) != null) {
            throw new ApiRequestException(
                    "Address '" + addressDTO.getStreetName() + " "
                            + addressDTO.getStreetName() + " with googleApiKey: "
                            + addressDTO.getGoogleApiId() + " , already exists.");
        }

        Address address = createNewAddressObject(addressDTO);
        address = addressRepository.save(address);

        return address;
    }

    private Address createNewAddressObject(AddressDTO addressDTO) {
        Address address = new Address();
        address.setGoogleApiId(addressDTO.getGoogleApiId());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setStreetName(addressDTO.getStreetName());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setLatitude(addressDTO.getLatitude());
        address.setLongitude(addressDTO.getLongitude());

        return address;
    }

}
