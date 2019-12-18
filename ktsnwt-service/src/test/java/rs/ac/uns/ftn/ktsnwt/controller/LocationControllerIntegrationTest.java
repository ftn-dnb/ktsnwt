package rs.ac.uns.ftn.ktsnwt.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.AddressConstants;
import rs.ac.uns.ftn.ktsnwt.constants.LocationConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepository;
import rs.ac.uns.ftn.ktsnwt.repository.LocationRepository;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;


    @LocalServerPort
    private int port;


    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );
        ResponseEntity<UserDTO> response = restTemplate.postForEntity("http://localhost:8080/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(headers);
    }


    @Test
    public void whenFindByValidId_thenReturnOk(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        ResponseEntity<LocationDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/locations/" + l.getId(), HttpMethod.GET, createHttpEntity(), LocationDTO.class);

        LocationDTO responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals(l.getId(), responseBody.getId());
        assertEquals(l.getName(), responseBody.getName());
        assertEquals(l.getAddress().getGoogleApiId(), responseBody.getAddress().getGoogleApiId());
    }

    @Test
    public void whenFindByInvalidId_thenReturnNotFound(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setId(LocationConstants.NOT_EXISTING_DB_ID);
        ResponseEntity<LocationDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/locations/" + l.getId(), HttpMethod.GET, createHttpEntity(), LocationDTO.class);
        LocationDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertNull(responseBody.getName());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findAllOnPage_thenReturnOk(){
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/api/locations/all/?page=" + LocationConstants.VALID_PAGE + "&size=" + LocationConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void whenFindAllOnNotExistingPage_thenReturnOk(){
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/api/locations/all/?page=" + LocationConstants.NOT_EXISTING_PAGE + "&size=" + LocationConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new ArrayList<>(), response.getBody());
    }

    @Test
    public void findAllOnInvalidPage_thenReturnBadRequest(){
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/api/locations/all/?page=" + LocationConstants.NOT_VALID_PAGE + "&size=" + LocationConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void whenFindAllWithInvalidSize_thenReturnBadRequest(){
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/api/locations/all/?page=" + LocationConstants.VALID_PAGE + "&size=" + LocationConstants.NOT_VALID_SIZE, HttpMethod.GET, createHttpEntity(), Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void whenFindByExistingName_thenReturnOk(){
        String name =  LocationConstants.EXISTING_DB_NAME;
        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations/name/" + name, HttpMethod.GET, createHttpEntity(), LocationDTO.class);
        LocationDTO l = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(l);
        assertEquals(name, l.getName());
    }

    @Test
    public void whenFindByNotExistingName_thenReturnNotFound(){
        String name =  LocationConstants.NOT_EXISTING_DB_NAME;
        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations/name/" + name, HttpMethod.GET, createHttpEntity(), LocationDTO.class);
        LocationDTO l = response.getBody();

        assertNotNull(l);
        assertNull(l.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void whenAddUniqueLocation_thenReturnOk(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<LocationDTO> request = new HttpEntity<>(l, headers);
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();


        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations", HttpMethod.POST, request, LocationDTO.class);
        List<Location> newList = locationRepository.findAll();
        LocationDTO newL = response.getBody();

        deleteRedundantLocationWithAddress(oldList, oldListA);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(oldList.size()+1, newList.size());
        assertNotNull(newL);
        assertEquals(l.getName(), newL.getName());
        assertEquals(l.getAddress().getGoogleApiId(), newL.getAddress().getGoogleApiId());

    }

    @Test
    public void whenAddLocationEmptyName_thenReturnBadRequest(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.setName("");
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<LocationDTO> request = new HttpEntity<>(l, headers);

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations", HttpMethod.POST, request, LocationDTO.class);
        List<Location> newList = locationRepository.findAll();

        deleteRedundantLocationWithAddress(oldList, oldListA);
        assertBadAddingLocation(response);
        assertEquals(oldList.size(), newList.size());
    }

    @Test
    public void whenAddLocationNotFilledAddress_thenReturnBadRequest(){
        LocationDTO l = LocationConstants.returnNewLocationDTONotFilledAddress();
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<LocationDTO> request = new HttpEntity<>(l, headers);

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations", HttpMethod.POST, request, LocationDTO.class);
        List<Location> newList = locationRepository.findAll();

        deleteRedundantLocationWithAddress(oldList, oldListA);
        assertBadAddingLocation(response);
        assertEquals(oldList.size(), newList.size());
    }


    @Test
    public void whenAddExistingLocation_thenReturnBadRequest(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<LocationDTO> request = new HttpEntity<>(l, headers);

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations", HttpMethod.POST, request, LocationDTO.class);
        List<Location> newList = locationRepository.findAll();

        deleteRedundantLocationWithAddress(oldList, oldListA);
        assertBadAddingLocation(response);
        assertEquals(oldList.size(), newList.size());
    }



    private void deleteRedundantLocationWithAddress(List<Location> oldListL, List<Address> oldListA){
        List<Location> newListL = locationRepository.findAll();
        if(oldListL.size() != newListL.size()){
            locationRepository.delete(newListL.get(newListL.size() - 1));
        }

        List<Address> newListA = addressRepository.findAll();
        if(oldListA.size() != newListA.size()){
            addressRepository.delete(newListA.get(newListA.size() - 1));
        }
    }


    private void assertBadAddingLocation(ResponseEntity<LocationDTO> response){
        LocationDTO locationDTO = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(locationDTO);
        assertNull(locationDTO.getId());
    }



    @Test
    public void whenEditToUniqueLocation_thenReturnOk(){
        // original
        Optional<Location> oldDBOptL = locationRepository.findById(LocationConstants.DB_ID);
        assertTrue(oldDBOptL.isPresent());
        Location oldDBLocation = oldDBOptL.get();

        // new
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setName(LocationConstants.NEW_DB_NAME);

        // request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<LocationDTO> request = new HttpEntity<>(l, headers);
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations/", HttpMethod.PUT, request, LocationDTO.class);
        LocationDTO editedL = response.getBody();
        Optional<Location> newDBOptL = locationRepository.findById(LocationConstants.DB_ID);

        //if something is added
        deleteRedundantLocationWithAddress(oldList, oldListA);

        // back to old db state
        locationRepository.save(oldDBLocation);
        assertEquals(oldList, locationRepository.findAll());

        // make sure object was edited
        assertTrue(newDBOptL.isPresent());
        Location dbLocation = newDBOptL.get();

        assertNotNull(editedL);
        assertEquals(editedL.getName(), l.getName());
        assertEquals(editedL.getAddress().getGoogleApiId(), l.getAddress().getGoogleApiId());
        assertEquals(l.getAddress().getGoogleApiId(), dbLocation.getAddress().getGoogleApiId());
        assertEquals(l.getName(), dbLocation.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenEditLocationEmptyName_thenReturnBadRequest(){
        Optional<Location> oldLOpt = locationRepository.findById(LocationConstants.DB_ID);
        assertTrue(oldLOpt.isPresent());
        Location oldL = oldLOpt.get();
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setName("");

        assertBadEditingLocation(oldL, l);
    }

    @Test
    public void whenEditToExistingLocation_thenReturnBadRequest(){
        Optional<Location> oldLOpt = locationRepository.findById(LocationConstants.DB_ID);
        assertTrue(oldLOpt.isPresent());
        Location oldL = oldLOpt.get();
        LocationDTO l = LocationConstants.returnDBLocationDTO();

        assertBadEditingLocation(oldL, l);
    }


    @Test
    public void whenEditNotExistingLocation_thenReturnBadRequest(){
        Optional<Location> oldLOpt = locationRepository.findById(LocationConstants.NOT_EXISTING_DB_ID);
        assertTrue(oldLOpt.isPresent());
        Location oldL = oldLOpt.get();
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setId(LocationConstants.NOT_EXISTING_DB_ID);
        l.setName(LocationConstants.NOT_EXISTING_DB_NAME);

        assertBadEditingLocation(oldL, l);
    }


    private void assertBadEditingLocation(Location oldL, LocationDTO newL){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<LocationDTO> request = new HttpEntity<>(newL, headers);
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations/", HttpMethod.PUT, request, LocationDTO.class);
        LocationDTO editedL = response.getBody();
        Optional<Location> newDBOptL = locationRepository.findById(oldL.getId());

        deleteRedundantLocationWithAddress(oldList, oldListA);

        //back to old db state
        locationRepository.save(oldL);
        assertEquals(oldList, locationRepository.findAll());

        if(newDBOptL.isPresent()){
            Location newDBL = newDBOptL.get();

            //database state not changed
            assertEquals(newDBL.getName(), oldL.getName());
            assertEquals(newDBL.getAddress().getGoogleApiId(), oldL.getAddress().getGoogleApiId());
        }

        //response returned error
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(editedL);
        assertNull(editedL.getId());
    }


    @Test
    public void whenChangeAddressToNotValid_thenReturnBadRequest(){
        AddressDTO a = AddressConstants.createNewAddressDto();
        Long id = LocationConstants.DB_ID;
        a.setGoogleApiId("");
        a.setCountry("");


        // original
        Optional<Location> oldDBOptL = locationRepository.findById(id);
        assertTrue(oldDBOptL.isPresent());
        Location oldDBLocation = oldDBOptL.get();

        // request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<AddressDTO> request = new HttpEntity<>(a, headers);
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations/address/" + id, HttpMethod.PUT, request, LocationDTO.class);
        LocationDTO editedL = response.getBody();


        //if something is added
        locationRepository.save(oldDBLocation);
        deleteRedundantLocationWithAddress(oldList, oldListA);

        // back to old db state
        assertEquals(oldList.size(), locationRepository.findAll().size());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(editedL);
        assertNull(editedL.getId());


    }

    @Test
    public void whenAddressChangeToValid_thenReturnOk(){
        AddressDTO a = AddressConstants.createNewAddressDto();
        Long id = LocationConstants.DB_ID;

        // original
        Optional<Location> oldDBOptL = locationRepository.findById(id);
        assertTrue(oldDBOptL.isPresent());
        Location oldDBLocation = oldDBOptL.get();

        // request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<AddressDTO> request = new HttpEntity<>(a, headers);
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations/address/" + id, HttpMethod.PUT, request, LocationDTO.class);
        Optional<Location> newDBOptL = locationRepository.findById(id);

        locationRepository.save(oldDBLocation);

        //if something is added
        deleteRedundantLocationWithAddress(oldList, oldListA);

        // back to old db state
        assertEquals(oldList.size(), locationRepository.findAll().size());

        assertTrue(newDBOptL.isPresent());
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    public void whenChangeAddressOfNotExistingLocation_thenReturnBadRequest(){
        AddressDTO a = AddressConstants.createNewAddressDto();
        Long id = LocationConstants.NOT_EXISTING_DB_ID;

        // request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<AddressDTO> request = new HttpEntity<>(a, headers);
        List<Location> oldList = locationRepository.findAll();
        List<Address> oldListA = addressRepository.findAll();

        ResponseEntity<LocationDTO> response = restTemplate.exchange("http://localhost:8080/api/locations/address/" + id, HttpMethod.PUT, request, LocationDTO.class);


        LocationDTO editedL = response.getBody();


        //if something is added
        deleteRedundantLocationWithAddress(oldList, oldListA);
        assertEquals(oldList.size(), locationRepository.findAll().size());

        assertNotNull(editedL);
        assertNull(editedL.getId());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }


}
