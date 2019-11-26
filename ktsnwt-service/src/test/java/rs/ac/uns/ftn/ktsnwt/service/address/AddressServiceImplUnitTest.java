package rs.ac.uns.ftn.ktsnwt.service.address;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.AddressConstants;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressServiceImplUnitTest {

    @Autowired
    private AddressServiceImpl addressService;

    @MockBean
    private AddressRepository addressRepository;

    @Before
    public void setUp() {
        Address address = new Address();
        // Do we need other fields ?
        address.setId(AddressConstants.MOCK_ID);
        address.setGoogleApiId(AddressConstants.MOCK_GOOGLE_API_ID);

        Mockito.when(addressRepository.findById(AddressConstants.MOCK_ID)).thenReturn(Optional.of(address));
        Mockito.when(addressRepository.findByGoogleApiId(AddressConstants.MOCK_GOOGLE_API_ID)).thenReturn(address);
        Mockito.when(addressRepository.save(any(Address.class))).thenReturn(address);
    }

    @Test
    public void whenFindByIdReturnAddress() {
        Long id = AddressConstants.MOCK_ID;
        Address address = addressService.findById(id);
        assertEquals(id, address.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByIdThrowResourceNotFound() {
        Address address = addressService.findById(AddressConstants.NON_EXISTING_DB_ID);
    }

    @Test
    public void whenFindByGoogleApiIdReturnAddress() {
        String id = AddressConstants.MOCK_GOOGLE_API_ID;
        Address address = addressService.findByGoogleApiId(id);
        assertEquals(id, address.getGoogleApiId());
        assertEquals(AddressConstants.MOCK_ID, address.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByGoogleApiIdThrowResourceNotFoundException() {
        Address address = addressService.findByGoogleApiId(AddressConstants.MOCK_GOOGLE_API_ID);
    }

    @Test
    public void whenAddAddressReturnAddress() {
        AddressDTO addressDto = AddressConstants.createNewAddressDto();
        Address address = addressService.addAddress(addressDto);
        Mockito.when(addressRepository.findByGoogleApiId(address.getGoogleApiId())).thenReturn(null);
        assertEquals(AddressConstants.MOCK_ID, address.getId());
    }

    @Test(expected = ApiRequestException.class)
    public void whenAddAddressGoogleApiIdExistsThrowException() {
        AddressDTO addressDto = AddressConstants.createNewAddressDto();
        addressDto.setGoogleApiId(AddressConstants.MOCK_GOOGLE_API_ID);
        Address address = addressService.addAddress(addressDto);
    }
}
