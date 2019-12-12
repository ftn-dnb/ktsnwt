package rs.ac.uns.ftn.ktsnwt.service.hall;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.HallConstants;
import rs.ac.uns.ftn.ktsnwt.constants.LocationConstants;
import rs.ac.uns.ftn.ktsnwt.constants.SectorConstants;
import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;
import rs.ac.uns.ftn.ktsnwt.repository.LocationRepository;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HallServiceImplUnitTest {

    @Autowired
    private HallServiceImpl hallService;

    @MockBean
    private HallRepository hallRepositoryMock;

    @MockBean
    private LocationRepository locationRepositoryMock;


    @Before
    public void setUp(){
        //data
        List<Hall> allOnLocation = new ArrayList<>();
        allOnLocation.add(new Hall(1L, "Mala sala", new HashSet<>(), new Location()));
        allOnLocation.add(new Hall(2L, "Velika sala", new HashSet<>(), new Location()));


        //mocking
        Mockito.when(hallRepositoryMock.findById(HallConstants.MOCK_ID)).thenReturn(java.util.Optional.of(new Hall(HallConstants.MOCK_ID, HallConstants.EXISTING_DB_NAME, new HashSet<>(), new Location(HallConstants.EXISTING_DB_LOCATION_ID, LocationConstants.EXISTING_DB_NAME, new Address(), new HashSet<>()))));
        Mockito.when(hallRepositoryMock.findById(HallConstants.NOT_EXISTING_DB_ID)).thenReturn(Optional.empty());
        Mockito.when(hallRepositoryMock.findById(HallConstants.NEXT_DB_ID)).thenReturn(Optional.empty());

        Mockito.when(hallRepositoryMock.getByLocationId(HallConstants.EXISTING_DB_LOCATION_ID, PageRequest.of(HallConstants.EXISTING_PAGE, HallConstants.VALID_SIZE))).thenReturn(new PageImpl<>(allOnLocation));
        Mockito.when(hallRepositoryMock.getByLocationId(HallConstants.EXISTING_DB_LOCATION_ID, PageRequest.of(HallConstants.NOT_EXISTING_PAGE, HallConstants.VALID_SIZE))).thenReturn(new PageImpl<>(new ArrayList<>()));
        Mockito.when(hallRepositoryMock.getByLocationId(HallConstants.NOT_EXISTING_DB_LOCATION_ID, PageRequest.of(HallConstants.EXISTING_PAGE, HallConstants.VALID_SIZE))).thenReturn(new PageImpl<>(new ArrayList<>()));

        Mockito.when(hallRepositoryMock.save(any(Hall.class))).thenReturn(new Hall());

        Mockito.when(hallRepositoryMock.findByName(HallConstants.EXISTING_DB_NAME,HallConstants.EXISTING_DB_LOCATION_ID)).thenReturn(new Hall(HallConstants.EXISTING_DB_ID, HallConstants.EXISTING_DB_NAME, new HashSet<>(), new Location()));
        Mockito.when(hallRepositoryMock.findByName(HallConstants.NEW_NAME, HallConstants.EXISTING_DB_LOCATION_ID)).thenReturn(null);

        Mockito.when(locationRepositoryMock.findById(HallConstants.EXISTING_DB_LOCATION_ID)).thenReturn(Optional.of(new Location(HallConstants.EXISTING_DB_LOCATION_ID, LocationConstants.EXISTING_DB_NAME, new Address(), new HashSet<>())));
        Mockito.when(locationRepositoryMock.findById(HallConstants.NOT_EXISTING_DB_LOCATION_ID)).thenReturn(Optional.empty());
    }


    @Test
    public void whenFindByValidId_thenReturnSector(){
        Hall h = hallService.findHallById(HallConstants.EXISTING_DB_ID);
        assertEquals(HallConstants.EXISTING_DB_NAME, h.getName());
        assertEquals(HallConstants.EXISTING_DB_ID, h.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByInvalidId_thenThrowResourceNotFoundException(){
        hallService.findHallById(HallConstants.NOT_EXISTING_DB_ID);
    }


    @Test
    public void whenFindAllByValidLocationId_thenReturnHalls(){
        List<Hall> halls = hallService.findAllById(HallConstants.EXISTING_DB_LOCATION_ID, HallConstants.EXISTING_PAGE, HallConstants.VALID_SIZE);

        for(int i = 1; i <= halls.size(); i += 1){
            long j = i;
            assertTrue(j == halls.get(i-1).getId());
        }
        assertTrue(halls.size() <= HallConstants.VALID_SIZE);
    }


    @Test
    public void whenFindAllByInvalidLocationId_thenReturnEmptyList(){
        List<Hall> halls = hallService.findAllById(HallConstants.NOT_EXISTING_DB_LOCATION_ID, HallConstants.EXISTING_PAGE, HallConstants.VALID_SIZE);
        assertEquals(new ArrayList<>(), halls);
    }


    @Test
    public void whenFindAllByLocationIdNotExistingPage_then(){
        List<Hall> h = hallService.findAllById(HallConstants.EXISTING_DB_LOCATION_ID, HallConstants.NOT_EXISTING_PAGE, HallConstants.VALID_SIZE);
        assertEquals(new ArrayList<>(), h);
    }


    @Test
    public void whenValidAddHall_thenReturnHall(){
        HallDTO hallToAdd = HallConstants.returnNewHall();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);

        assertNotNull(newHall);
        assertEquals(HallConstants.NEW_NAME, newHall.getName());
        assertEquals(HallConstants.EXISTING_DB_LOCATION_ID, newHall.getLocation().getId());
    }


    @Test(expected = ResourceAlreadyExistsException.class)
    public void whenExistingAddHall_thenReturnResourceAlreadyExistsException(){
        HallDTO hallToAdd = HallConstants.returnExistingHall();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void whenAddHallNotExistingLocation_thenReturnResourceNotFoundException(){
        HallDTO hallToAdd = HallConstants.returnHallOnNotExistingLocation();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenAddHallEmptyName_thenReturnBadAttributeValueException(){
        HallDTO hallToAdd = HallConstants.returnHallWithEmptyName();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);
    }



    @Test
    public void whenValidEditHall_thenReturnHall(){
        HallDTO hallToEdit = HallConstants.returnNewHall();
        hallToEdit.setId(HallConstants.EXISTING_DB_ID);
        Hall res = hallService.editHall(hallToEdit);

        assertNotNull(res);
        assertEquals(hallToEdit.getId(), res.getId());
        assertEquals(hallToEdit.getName(), res.getName());
        assertEquals(hallToEdit.getLocationId(), res.getLocation().getId());
    }


    @Test(expected = ResourceAlreadyExistsException.class)
    public void whenExistingEditHall_thenThrowResourceAlreadyExistsException(){
        HallDTO hallToEdit = HallConstants.returnNewHall();
        hallToEdit.setId(HallConstants.EXISTING_DB_ID);
        hallToEdit.setName(HallConstants.ANOTHER_EXISTING_DB_NAME);
        Hall res = hallService.editHall(hallToEdit);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenEditHallEmptyName_thenThrowBadAttributeValueException(){
        HallDTO hallToEdit = HallConstants.returnNewHall();
        hallToEdit.setId(HallConstants.EXISTING_DB_ID);
        hallToEdit.setName("");
        Hall res = hallService.editHall(hallToEdit);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void whenEditNotExistingHall_thenThrowResourceNotFoundException(){
        HallDTO hallToEdit = HallConstants.returnNewHall();
        Hall res = hallService.editHall(hallToEdit);
    }


    @After
    public void tearDown(){

    }


}
