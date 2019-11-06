package rs.ac.uns.ftn.ktsnwt.service.address;


import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.model.Address;

public interface AddressService {

    Address findById(Long id);
    Address findByGoogleApiId(String googleApiId);
    Address addAddress(AddressDTO addressDTO);

}
