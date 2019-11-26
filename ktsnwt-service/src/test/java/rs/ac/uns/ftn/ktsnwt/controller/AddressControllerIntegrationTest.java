package rs.ac.uns.ftn.ktsnwt.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.AddressConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepository;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(headers);
    }

    private void checkAddressDto(AddressDTO address) {
        assertEquals(AddressConstants.DB_ID, address.getId());
        assertEquals(AddressConstants.DB_CITY, address.getCity());
        assertEquals(AddressConstants.DB_COUNTRY, address.getCountry());
        assertEquals(AddressConstants.DB_GOOGLE_API_ID, address.getGoogleApiId());
        // Da li je dobro porediti double-ove ?
        // assertEquals(AddressConstants.DB_LATITUDE, address.getLatitude());
        // assertEquals(AddressConstants.DB_LONGITUDE, address.getLongitude());
        assertEquals(AddressConstants.DB_POSTAL_CODE, address.getPostalCode());
        assertEquals(AddressConstants.DB_STREET_NAME, address.getStreetName());
        assertEquals(AddressConstants.DB_STREET_NUMBER, address.getStreetNumber());
    }

    @Test
    public void whenGetAddressByIdReturnAddress() {
        ResponseEntity<AddressDTO> response = restTemplate.exchange(
                "/api/address/" + AddressConstants.DB_ID, HttpMethod.GET, createHttpEntity(), AddressDTO.class);

        AddressDTO address = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(address);
        checkAddressDto(address);
    }

    @Test
    public void whenGetAddressByIdReturnNotFound() {
        ResponseEntity<AddressDTO> response = restTemplate.exchange(
                "/api/address/" + AddressConstants.NON_EXISTING_DB_ID, HttpMethod.GET, createHttpEntity(), AddressDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenGetAddressByGoogleApiIdReturnAddress() {
        ResponseEntity<AddressDTO> response = restTemplate.exchange(
                "/api/address/googleApi/" + AddressConstants.DB_GOOGLE_API_ID, HttpMethod.GET, createHttpEntity(), AddressDTO.class);

        AddressDTO address = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(address);
        checkAddressDto(address);
    }

    @Test
    public void whenGetAddressByGoogleApiIdNotFound() {
        ResponseEntity<AddressDTO> response = restTemplate.exchange(
                "/api/address/googleApi/" + AddressConstants.NON_EXISTING_DB_GOOGLE_API_ID, HttpMethod.GET, createHttpEntity(), AddressDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenAddAddressReturnNewAddress() {
        int sizeBeforeInsert = addressRepository.findAll().size();

        AddressDTO newAddressDto = createAddressDtoForInsert();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<AddressDTO> request = new HttpEntity<>(newAddressDto, headers);

        ResponseEntity<AddressDTO> response = restTemplate.exchange("/api/address", HttpMethod.POST, request, AddressDTO.class);
        AddressDTO address = response.getBody();

        // Check HTTP response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(address);

        // After insert we should have 1 new element in db
        int sizeAfterInsert = addressRepository.findAll().size();
        assertEquals(sizeBeforeInsert + 1, sizeAfterInsert);

        // Last element should be our new Address object
        List<Address> addresses = addressRepository.findAll();
        Address lastAddress = addresses.get(addresses.size() - 1);

        // Check if correct DTO was sent
        checkAddressDto(lastAddress, address);

        // Delete new address from repository
        addressRepository.delete(lastAddress);
    }

    private AddressDTO createAddressDtoForInsert() {
        AddressDTO address = new AddressDTO();
        address.setCity(AddressConstants.NEW_DB_CITY);
        address.setCountry(AddressConstants.NEW_DB_COUNTRY);
        address.setGoogleApiId(AddressConstants.NEW_DB_GOOGLE_API_ID);
        address.setLatitude(AddressConstants.NEW_DB_LATITUDE);
        address.setLongitude(AddressConstants.NEW_DB_LONGITUDE);
        address.setPostalCode(AddressConstants.NEW_DB_POSTAL_CODE);
        address.setStreetName(AddressConstants.NEW_DB_STREET_NAME);
        address.setStreetNumber(AddressConstants.NEW_DB_STREET_NUMBER);

        return address;
    }

    private void checkAddressDto(Address address, AddressDTO addressDto) {
        assertEquals(address.getId(), addressDto.getId());
        assertEquals(address.getCity(), addressDto.getCity());
        assertEquals(address.getCountry(), addressDto.getCountry());
        assertEquals(address.getGoogleApiId(), addressDto.getGoogleApiId());

        // Da li se porede double-ovi i float-ovi ?
        // assertEquals((float) address.getLatitude(), (float) addressDto.getLatitude());
        // assertEquals((float) address.getLongitude(), (float) addressDto.getLongitude());

        assertEquals(address.getPostalCode(), addressDto.getPostalCode());
        assertEquals(address.getStreetName(), addressDto.getStreetName());
        assertEquals(address.getStreetNumber(), addressDto.getStreetNumber());
    }

    @Test
    public void whenAddAddressWithSameGoogleApiIdThrowBadRequest() {
        int sizeBeforeInsert = addressRepository.findAll().size();

        // Create new address dto, but with existing google api id
        AddressDTO newAddressDto = createAddressDtoForInsert();
        newAddressDto.setGoogleApiId(AddressConstants.DB_GOOGLE_API_ID);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<AddressDTO> request = new HttpEntity<>(newAddressDto, headers);
        ResponseEntity<AddressDTO> response = restTemplate.exchange("/api/address", HttpMethod.POST, request, AddressDTO.class);

        // Check HTTP response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Number of elements in db should remain the same
        assertEquals(sizeBeforeInsert, addressRepository.findAll().size());
    }
}
