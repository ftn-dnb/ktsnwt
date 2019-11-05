package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.model.Address;

public class AddressMapper {

    private AddressMapper() {

    }

    public static AddressDTO toDto(Address address) { return new AddressDTO(address); }
}
