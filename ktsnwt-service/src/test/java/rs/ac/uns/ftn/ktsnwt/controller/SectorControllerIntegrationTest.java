package rs.ac.uns.ftn.ktsnwt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.SectorConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.SectorMapper;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;
import rs.ac.uns.ftn.ktsnwt.repository.SectorRepository;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SectorControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    HallRepository hallRepository;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private String accessToken;


    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

//        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        HttpEntity<JwtAuthenticationRequest> loginEntity = new HttpEntity<>(loginDto);
        ResponseEntity<UserDTO> response = restTemplate.exchange("/auth/login", HttpMethod.POST, loginEntity ,UserDTO.class);

        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(headers);
    }


    @Test
    public void whenValidGetSectorById_thenReturnSector(){
        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors/" + SectorConstants.EXISTING_DB_ID, HttpMethod.GET, createHttpEntity(), SectorDTO.class);
        SectorDTO s = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(s);


    }

    @Test
    public void whenInvalidGetSectorById_thenReturnNotFound(){
        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors/" + SectorConstants.NOT_EXISTING_DB_ID, HttpMethod.GET, createHttpEntity(), SectorDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenGetAllSectorsByHall_thenReturnSectors() throws JsonProcessingException {
        List<SectorDTO> sectors = new ArrayList<>();

        ResponseEntity<List<SectorDTO>> response = restTemplate.exchange(
                "/api/sectors/all/" + SectorConstants.EXISTING_DB_HALL_ID + "?page=" + SectorConstants.EXISTING_PAGE + "&size=" + SectorConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), new ParameterizedTypeReference<List<SectorDTO>>() {
                });
        assertNotNull(response.getBody());
        sectors = response.getBody();

        Page<Sector> s = sectorRepository.getByHallId(SectorConstants.EXISTING_DB_HALL_ID, PageRequest.of(SectorConstants.EXISTING_PAGE, SectorConstants.VALID_SIZE));
        List<SectorDTO> actual = SectorMapper.toListDto(s.toList());

        assertTrue(sectors.size() <= SectorConstants.VALID_SIZE);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        areListsOfSectorDTOsEqual(actual, sectors);

    }

    private void areListsOfSectorDTOsEqual(List<SectorDTO> l1, List<SectorDTO> l2){
        int s1 = l1.size();
        int s2 = l2.size();

        assertEquals(s1,s2);
        for(int i = s1 - 1; i >= 0; i--){
            areSectorDTOsEqual(l1.get(i), l2.get(i));
        }
    }

    @Test
    public void whenFindAllByInvalidHallId_thenReturnEmptyList(){
        List<SectorDTO> sectors = new ArrayList<>();

        ResponseEntity<List<SectorDTO>> response = restTemplate.exchange(
                "/api/sectors/all/" + SectorConstants.NOT_EXISTING_HALL_ID + "?page=" + SectorConstants.EXISTING_PAGE + "&size=" + SectorConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), new ParameterizedTypeReference<List<SectorDTO>>() {
                });
        assertNotNull(response.getBody());
        sectors = response.getBody();

        assertEquals(0, sectors.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void whenGetAllSectorsOnInvalidPage(){
        ResponseEntity<Object> response = restTemplate.exchange(
                "/api/sectors/all/" + SectorConstants.EXISTING_DB_HALL_ID + "?page=" + SectorConstants.NOT_VALID_PAGE + "&size=" + SectorConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(),Object.class);
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void whenGetAllSectorsOnNotExistingPage(){
        ResponseEntity<List<SectorDTO>> response = restTemplate.exchange(
                "/api/sectors/all/" + SectorConstants.EXISTING_DB_HALL_ID + "?page=" + SectorConstants.NOT_EXISTING_PAGE + "&size=" + SectorConstants.VALID_SIZE, HttpMethod.GET, createHttpEntity(), new ParameterizedTypeReference<List<SectorDTO>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), new ArrayList<>());
        assertEquals(response.getBody().size(), 0);
    }

    @Test
    public void whenGetAllSectorsWithInvalidSize(){
        ResponseEntity<Object> response = restTemplate.exchange(
                "/api/sectors/all/" + SectorConstants.EXISTING_DB_HALL_ID + "?page=" + SectorConstants.EXISTING_PAGE + "&size=" + SectorConstants.NOT_VALID_SIZE, HttpMethod.GET, createHttpEntity(),Object.class);
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }



    @Test
    public void whenAddUniqueSector_thenReturnSector(){
        SectorDTO newSector = SectorConstants.createNewSectorDTO();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(newSector, headers);
        int oldSize = sectorRepository.findAll().size();

        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.POST, request,SectorDTO.class);

        //make sure there's no problem with http request
        SectorDTO s = response.getBody();
        assertNotNull(response.getBody());
        assertNotNull(s);
        assertNotNull(s.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        newSector.setId(s.getId());           //id is generated when writing to database

        //new sector is returned and written to db
        List<Sector> all = sectorRepository.findAll();
        int newSize = all.size();
        Sector lastSector = all.get(all.size() - 1);
        areSectorsEqual(newSector, lastSector);
        areSectorDTOsEqual(newSector, s);
        assertEquals(oldSize + 1, newSize);

        //back to old db state
        sectorRepository.delete(lastSector);
        assertEquals(sectorRepository.findAll().size(), oldSize);

    }

    private void areSectorDTOsEqual(SectorDTO s1, SectorDTO s2){
        assertEquals(s1.getId(), s2.getId());
        assertEquals(s1.getHallId(), s2.getHallId());
        assertEquals(s1.getName(), s2.getName());
        assertEquals(s1.getCapacity(), s2.getCapacity());
        assertEquals(s1.getNumColumns(), s2.getNumColumns());
        assertEquals(s1.getNumRows(), s2.getNumRows());
        assertEquals(s1.getType(), s2.getType());
    }
    private void areSectorsEqual(SectorDTO s1, Sector s2){
        assertEquals(s1.getId(), s2.getId());
        assertEquals(s1.getHallId(), s2.getHall().getId());
        assertEquals(s1.getName(), s2.getName());
        assertEquals(s1.getCapacity(), s2.getCapacity());
        assertEquals(s1.getNumColumns(), s2.getNumColumns());
        assertEquals(s1.getNumRows(), s2.getNumRows());
        assertEquals(s1.getType(), s2.getType());
    }


    @Test
    public void whenAddSectorExistingNameAndHallId(){
        SectorDTO existSector = SectorConstants.createExistingSectorDTO();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(existSector, headers);
        List<Sector> oldList = sectorRepository.findAll();

        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.POST, request,SectorDTO.class);

        deleteRedundantSector(oldList);

        assertBadAddingSector(response);
    }

    @Test
    public void whenAddSectorNonExistingHallId(){
        List<Sector> oldList = sectorRepository.findAll();
        SectorDTO existSector = SectorConstants.createSectorDTOWithNotExistingHallId();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(existSector, headers);
        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                    "/api/sectors", HttpMethod.POST, request,SectorDTO.class);

        deleteRedundantSector(oldList);

        assertBadAddingSector(response);
    }


    @Test
    public void whenAddSectorWithInvalidCalculatingCapacity(){
        SectorDTO existSector = SectorConstants.createSectorDTOInvalidCalculatingCapacity();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(existSector, headers);
        List<Sector> oldList = sectorRepository.findAll();


        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.POST, request,SectorDTO.class);


        deleteRedundantSector(oldList);

        assertBadAddingSector(response);
    }

    @Test
    public void whenAddSectorWithInvalidCapacity(){
        SectorDTO existSector = SectorConstants.createSectorDTOInvalidCapacity();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(existSector, headers);
        List<Sector> oldList = sectorRepository.findAll();


        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.POST, request,SectorDTO.class);

        deleteRedundantSector(oldList);

        assertBadAddingSector(response);
    }

    @Test
    public void whenAddSeatSectorWithInvalidRows(){
        SectorDTO existSector = SectorConstants.createSeatSectorDTOInvalidRows();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(existSector, headers);
        List<Sector> oldList = sectorRepository.findAll();


        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.POST, request,SectorDTO.class);

        deleteRedundantSector(oldList);

        assertBadAddingSector(response);
    }

    @Test
    public void whenAddSeatSectorWithInvalidColumns(){
        SectorDTO existSector = SectorConstants.createSeatSectorDTOInvalidColumns();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(existSector, headers);
        List<Sector> oldList = sectorRepository.findAll();

        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.POST, request,SectorDTO.class);

        deleteRedundantSector(oldList);

        assertBadAddingSector(response);
    }


    @Test
    public void whenAddSectorEmptyName(){
        SectorDTO sector = SectorConstants.createNewSectorDTO();
        sector.setName("");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(sector, headers);
        List<Sector> oldList = sectorRepository.findAll();

        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.POST, request,SectorDTO.class);

        deleteRedundantSector(oldList);

        assertBadAddingSector(response);
    }



    private void deleteRedundantSector(List<Sector> oldList){
        List<Sector> newList = sectorRepository.findAll();
        if(oldList.size() != newList.size()){
            sectorRepository.delete(newList.get(newList.size() - 1));
        }
    }


    private void assertBadAddingSector(ResponseEntity<SectorDTO> response){
        SectorDTO s = response.getBody();

        assertNotEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(s);
        assertNull(s.getId());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());


    }

    @Test
    public void whenEditSectorUnique_thenReturnSector(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        String oldName = newSector.getName();
        newSector.setName(SectorConstants.RANDOM_NAME);
        SectorDTO beforeEditing = new SectorDTO(newSector);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(beforeEditing, headers);

        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.PUT, request,SectorDTO.class);

        SectorDTO s = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(s);
        assertNotNull(s.getId());
        areSectorDTOsEqual(s,beforeEditing);

        Optional newOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        assertTrue(newOptional.isPresent());
        SectorDTO afterEditing = new SectorDTO((Sector)newOptional.get());
        areSectorDTOsEqual(afterEditing, beforeEditing);


        //back to old db state
        newSector.setName(oldName);
        sectorRepository.save(newSector);
    }


    @Test
    public void whenEditToExistingSector(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        List<Sector> oldList = sectorRepository.findAll();

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        newSector.setName(SectorConstants.EXISTING_NAME);   //existing name in existing hall
        SectorDTO beforeEditing = new SectorDTO(newSector);

        genericFunctionForCheckingInvalidEditingSectors(beforeEditing, oldList, newSector);


    }

    @Test
    public void whenEditWithNotExistingHallId(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        List<Sector> oldList = sectorRepository.findAll();

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        SectorDTO beforeEditing = new SectorDTO(newSector);
        beforeEditing.setHallId(SectorConstants.NOT_EXISTING_HALL_ID);

        genericFunctionForCheckingInvalidEditingSectors(beforeEditing, oldList, newSector);

    }

    @Test
    public void whenEditSectorWithInvalidCapacity(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        List<Sector> oldList = sectorRepository.findAll();

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        SectorDTO beforeEditing = new SectorDTO(newSector);
        beforeEditing.setHallId(SectorConstants.NEW_VALID_HALL_ID);
        beforeEditing.setCapacity(SectorConstants.NEW_INVALID_CAPACITY);

        genericFunctionForCheckingInvalidEditingSectors(beforeEditing, oldList, newSector);

    }

    @Test
    public void whenEditSectorWithInvalidCalculatedCapacity(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        List<Sector> oldList = sectorRepository.findAll();

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        SectorDTO beforeEditing = new SectorDTO(newSector);
        beforeEditing.setCapacity(SectorConstants.NEW_VALID_CAPACITY + 1);
        beforeEditing.setHallId(SectorConstants.NEW_VALID_HALL_ID);

        genericFunctionForCheckingInvalidEditingSectors(beforeEditing, oldList, newSector);

    }


    @Test
    public void whenEditSectorEmptyName(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        List<Sector> oldList = sectorRepository.findAll();

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        SectorDTO beforeEditing = new SectorDTO(newSector);
        beforeEditing.setName("");
        beforeEditing.setHallId(SectorConstants.NEW_VALID_HALL_ID);

        genericFunctionForCheckingInvalidEditingSectors(beforeEditing, oldList, newSector);

    }


    @Test
    public void whenEditSectorWithInvalidRows(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        List<Sector> oldList = sectorRepository.findAll();

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        SectorDTO beforeEditing = new SectorDTO(newSector);
        beforeEditing.setNumRows(SectorConstants.NEW_INVALID_ROWS);
        beforeEditing.setHallId(SectorConstants.NEW_VALID_HALL_ID);

        genericFunctionForCheckingInvalidEditingSectors(beforeEditing, oldList, newSector);

    }

    @Test
    public void whenEditSectorWithInvalidColumns(){
        Optional oldOptional = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);
        List<Sector> oldList = sectorRepository.findAll();

        //sector is present
        assertNotNull(oldOptional);
        assertTrue(oldOptional.isPresent());

        Sector newSector = (Sector)oldOptional.get();
        SectorDTO beforeEditing = new SectorDTO(newSector);
        beforeEditing.setHallId(SectorConstants.NEW_VALID_HALL_ID);
        beforeEditing.setNumColumns(SectorConstants.NEW_INVALID_COLUMNS);

        genericFunctionForCheckingInvalidEditingSectors(beforeEditing, oldList, newSector);
    }


    private void genericFunctionForCheckingInvalidEditingSectors(SectorDTO beforeEditing, List<Sector> oldList, Sector original){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        HttpEntity<SectorDTO> request = new HttpEntity<>(beforeEditing, headers);

        ResponseEntity<SectorDTO> response = restTemplate.exchange(
                "/api/sectors", HttpMethod.PUT, request,SectorDTO.class);

        List<Sector> newList = sectorRepository.findAll();
        Optional o = sectorRepository.findById(SectorConstants.EXISTING_DB_ID);


        if(o.isPresent()){
            Sector s = (Sector)o.get();
            SectorDTO s1 = new SectorDTO(original);
            SectorDTO s2 = new SectorDTO(s);

            // if something has changed
            if(!s1.equals(s2)){

                // back to old db state
                Hall h = hallRepository.getById(SectorConstants.ORIGINAL_HALL_ID);
                original.setName(SectorConstants.ORIGINAL_NAME);
                original.setCapacity(SectorConstants.ORIGINAL_CAPACITY);
                original.setHall(h);
                original.setNumColumns(SectorConstants.ORIGINAL_COLUMNS);
                original.setNumRows(SectorConstants.ORIGINAL_ROWS);
                sectorRepository.save(original);
                }
        }


        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(oldList, newList);
    }
}
