package rs.ac.uns.ftn.ktsnwt.service.address;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.AddressConstants;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepository;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressServiceImplIntegrationTest {

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void whenFindByIdReturnAddress() {
        Address address = addressService.findById(AddressConstants.DB_ID);
        assertEquals(AddressConstants.DB_ID, address.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByIdThrowResourceNotFoundException() {
        Address address = addressService.findById(AddressConstants.NON_EXISTING_DB_ID);
    }

    @Test
    public void whenFindByGoogleApiIdReturnAddress() {
        Address address = addressService.findByGoogleApiId(AddressConstants.DB_GOOGLE_API_ID);
        assertEquals(AddressConstants.DB_ID, address.getId());
        assertEquals(AddressConstants.DB_GOOGLE_API_ID, address.getGoogleApiId());
    }

    @Test(expected = NullPointerException.class)
    public void whenFindByGoogleApiIdThrowResourceNotFoundException() {
        Address address = addressService.findByGoogleApiId(AddressConstants.NON_EXISTING_DB_GOOGLE_API_ID);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenAddAddressReturnAddress() {
        int sizeBeforeInsert = addressRepository.findAll().size();

        AddressDTO addressDto = AddressConstants.createNewAddressDto();
        Address address = addressService.addAddress(addressDto);

        int sizeAfterInsert = addressRepository.findAll().size();
        assertEquals(sizeBeforeInsert + 1, sizeAfterInsert);

        assertEquals(address.getCity(), addressDto.getCity());
        assertEquals(address.getCountry(), addressDto.getCountry());
        assertEquals(address.getGoogleApiId(), addressDto.getGoogleApiId());
        assertEquals(address.getPostalCode(), addressDto.getPostalCode());
        assertEquals(address.getStreetName(), addressDto.getStreetName());
        assertEquals(address.getStreetNumber(), addressDto.getStreetNumber());
    }

    @Test(expected = ApiRequestException.class)
    @Transactional @Rollback(true)
    public void whenAddAddressWithExistingGoogleApiIdThrowException() {
        AddressDTO addressDto = AddressConstants.createNewAddressDto();
        addressDto.setGoogleApiId(AddressConstants.DB_GOOGLE_API_ID);
        Address address = addressService.addAddress(addressDto);
    }
}
