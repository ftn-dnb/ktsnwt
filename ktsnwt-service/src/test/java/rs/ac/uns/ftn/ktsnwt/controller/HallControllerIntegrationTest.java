package rs.ac.uns.ftn.ktsnwt.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.HttpResource;
import rs.ac.uns.ftn.ktsnwt.constants.HallConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HallControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;


    @LocalServerPort
    private int port;


    @Autowired
    private HallRepository hallRepository;




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
    public void whenGetHallByValidId_thenReturnOK(){
        ResponseEntity<HallDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/halls/" + HallConstants.EXISTING_DB_ID, HttpMethod.GET, createHttpEntity(), HallDTO.class);
        HallDTO h = response.getBody();

        assertNotNull(h);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HallConstants.EXISTING_DB_ID, h.getId());
        assertEquals(HallConstants.EXISTING_DB_LOCATION_ID, h.getLocationId());
        assertEquals(HallConstants.EXISTING_DB_NAME, h.getName());

    }

    @Test
    public void whenGetHallByInvalidId_thenReturnNotFound(){
        ResponseEntity<HallDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/halls/" + HallConstants.NOT_EXISTING_DB_ID, HttpMethod.GET, createHttpEntity(), HallDTO.class);
        HallDTO h = response.getBody();

        assertNotNull(h);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


    @Test
    public void whenGetAllHallsOnValidLocation_thenReturnOK(){
        ResponseEntity<List<HallDTO>> response = restTemplate.exchange("http://localhost:8080/api/halls/all/" + HallConstants.EXISTING_DB_LOCATION_ID + "?page=" + HallConstants.EXISTING_PAGE + "&size=" + HallConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), new ParameterizedTypeReference<List<HallDTO>>() {
        });

        List<HallDTO> h = response.getBody();
        assertNotNull(h);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, h.size());

    }


    @Test
    public void whenGetAllHallsOnInvalidLocation_thenHandleTheError(){
        ResponseEntity<List<HallDTO>> response = restTemplate.exchange("http://localhost:8080/api/halls/all/" + HallConstants.NOT_EXISTING_DB_LOCATION_ID + "?page=" + HallConstants.EXISTING_PAGE + "&size=" + HallConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), new ParameterizedTypeReference<List<HallDTO>>() {});

        List<HallDTO> h = response.getBody();
        assertNotNull(h);
        assertNotEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void whenGetAllHallsInvalidPage_thenHandleTheError(){
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/api/halls/all/" + HallConstants.EXISTING_DB_LOCATION_ID + "?page=" + HallConstants.NOT_VALID_PAGE + "&size=" + HallConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), Object.class);
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void whenGetAllHallsInvalidSize_thenHandleTheError(){
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/api/halls/all/" + HallConstants.EXISTING_DB_LOCATION_ID + "?page=" + HallConstants.EXISTING_PAGE + "&size=" + HallConstants.NOT_VALID_SIZE, HttpMethod.GET, createHttpEntity(), Object.class);
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }



    @Test
    public void whenGetAllHallsNotExistingPage_thenReturnOk(){
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/api/halls/all/" + HallConstants.EXISTING_DB_LOCATION_ID + "?page=" + HallConstants.NOT_EXISTING_PAGE + "&size=" + HallConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), Object.class);
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void whenValidAddHall_thenReturnOk(){
        HallDTO hall = HallConstants.returnNewHall();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<HallDTO> request = new HttpEntity<>(hall, headers);
        List<Hall> oldList = hallRepository.findAll();

        ResponseEntity<HallDTO> response = restTemplate.exchange("http://localhost:8080/api/halls/", HttpMethod.POST, request, HallDTO.class);
        List<Hall> newList = hallRepository.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        HallDTO newHall = response.getBody();
        assertEquals(newHall.getLocationId(), hall.getLocationId());
        assertEquals(newHall.getName(), hall.getName());
        assertEquals(oldList.size()+1, newList.size());

        Hall newdbhall = newList.get(newList.size()-1);
        assertEquals(hall.getName(), newdbhall.getName());
        assertEquals(hall.getLocationId(), newdbhall.getLocation().getId());

        //back to old db state
        hallRepository.delete(newdbhall);
        assertEquals(oldList.size(), hallRepository.findAll().size());


    }

    @Test
    public void whenAddExistingHall_thenReturnBadRequest(){
        HallDTO hall = HallConstants.returnExistingHall();
        List<Hall> oldList = hallRepository.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<HallDTO> request = new HttpEntity<>(hall, headers);

        ResponseEntity<HallDTO> response = restTemplate.exchange("http://localhost:8080/api/halls/", HttpMethod.POST, request, HallDTO.class);
        List<Hall> newList = hallRepository.findAll();

        deleteRedundantHall(oldList);
        assertBadAddingHall(response);

        assertEquals(newList.size(), oldList.size());


    }

    @Test
    public void whenAddHallNotExistingLocation_thenReturnBadRequest(){
        HallDTO hall = HallConstants.returnHallOnNotExistingLocation();
        List<Hall> oldList = hallRepository.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<HallDTO> request = new HttpEntity<>(hall, headers);

        ResponseEntity<HallDTO> response = restTemplate.exchange("http://localhost:8080/api/halls/", HttpMethod.POST, request, HallDTO.class);
        List<Hall> newList = hallRepository.findAll();

        deleteRedundantHall(oldList);
        assertBadAddingHall(response);

        assertEquals(newList.size(), oldList.size());
    }



    @Test
    public void whenAddHallEmptyName_thenReturnBadRequest(){
        HallDTO hall = HallConstants.returnHallWithEmptyName();
        List<Hall> oldList = hallRepository.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<HallDTO> request = new HttpEntity<>(hall, headers);

        ResponseEntity<HallDTO> response = restTemplate.exchange("http://localhost:8080/api/halls/", HttpMethod.POST, request, HallDTO.class);
        List<Hall> newList = hallRepository.findAll();

        deleteRedundantHall(oldList);
        assertBadAddingHall(response);

        assertEquals(newList.size(), oldList.size());
    }


    private void deleteRedundantHall(List<Hall> oldList){
        List<Hall> newList = hallRepository.findAll();
        if(oldList.size() != newList.size()){
            hallRepository.delete(newList.get(newList.size() - 1));
        }
    }


    private void assertBadAddingHall(ResponseEntity<HallDTO> response){
        HallDTO h = response.getBody();

        assertNotEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(h);
        assertNull(h.getId());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }




    @Test
    public void whenValidEditHall_thenReturnOk(){
        Hall oldHall = hallRepository.getById(HallConstants.EXISTING_DB_ID);
        HallDTO hall = HallConstants.returnExistingHall();
        hall.setName(HallConstants.NEW_NAME);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<HallDTO> request = new HttpEntity<>(hall, headers);
        List<Hall> oldList = hallRepository.findAll();

        ResponseEntity<HallDTO> response = restTemplate.exchange("http://localhost:8080/api/halls/", HttpMethod.PUT, request, HallDTO.class);
        List<Hall> newList = hallRepository.findAll();
        Hall newdbhall = hallRepository.getById(HallConstants.EXISTING_DB_ID);

        //back to old db state
        hallRepository.save(oldHall);
        assertEquals(oldList, hallRepository.findAll());

        assertEquals(oldList.size(), newList.size());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        HallDTO editedHall = response.getBody();
        assertEquals(editedHall.getLocationId(), hall.getLocationId());
        assertEquals(editedHall.getName(), hall.getName());

        assertEquals(hall.getName(), newdbhall.getName());
        assertEquals(hall.getLocationId(), newdbhall.getLocation().getId());

    }


    @Test
    public void whenEditToExistingHall_thenReturnBadRequest(){
        Hall oldHall = hallRepository.getById(HallConstants.EXISTING_DB_ID);
        HallDTO hall = HallConstants.returnExistingHall();
        hall.setName(HallConstants.ANOTHER_EXISTING_DB_NAME);
        hall.setId(HallConstants.EXISTING_DB_ID);


        assertBadEditingHall(oldHall, hall);
    }

    @Test
    public void whenEditHallEmptyName_thenReturnBadRequest(){
        Hall oldHall = hallRepository.getById(HallConstants.EXISTING_DB_ID);
        HallDTO hall = HallConstants.returnHallWithEmptyName();
        hall.setId(HallConstants.EXISTING_DB_ID);

        assertBadEditingHall(oldHall, hall);
    }



    @Test
    public void whenEditNotExistingHall_thenReturnBadRequest(){
        Hall oldHall = hallRepository.getById(HallConstants.EXISTING_DB_ID);
        HallDTO hall = HallConstants.returnNewHall();
        hall.setId(HallConstants.NOT_EXISTING_DB_ID);

        assertBadEditingHall(oldHall, hall);
    }


    private void assertBadEditingHall(Hall oldHall, HallDTO hall){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<HallDTO> request = new HttpEntity<>(hall, headers);
        List<Hall> oldList = hallRepository.findAll();

        ResponseEntity<HallDTO> response = restTemplate.exchange("http://localhost:8080/api/halls/", HttpMethod.PUT, request, HallDTO.class);


        HallDTO editedHall = response.getBody();
        Hall newdbhall = hallRepository.getById(HallConstants.EXISTING_DB_ID);

        //back to old db state
        hallRepository.save(oldHall);
        assertEquals(oldList, hallRepository.findAll());

        //database state not changed
        assertEquals(newdbhall.getName(), oldHall.getName());
        assertEquals(newdbhall.getLocation().getId(), oldHall.getLocation().getId());

        //response returned error
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(editedHall);
        assertNull(editedHall.getId());
    }

}
