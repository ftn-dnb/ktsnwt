package rs.ac.uns.ftn.ktsnwt.service.hall;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.ktsnwt.constants.HallConstants;
import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HallServiceImplIntegrationTest {

    @Autowired
    private HallServiceImpl hallService;

    @Autowired
    private HallRepository hallRepository;


    @Test
    public void whenFindByValidId_thenReturnSector(){
        Hall h = hallService.findHallById(HallConstants.EXISTING_DB_ID);
        assertEquals(HallConstants.EXISTING_DB_NAME, h.getName());
        assertEquals(HallConstants.EXISTING_DB_ID, h.getId());
        assertEquals(HallConstants.EXISTING_DB_LOCATION_ID, h.getLocation().getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByInvalidId_thenThrowResourceNotFoundException(){
        hallService.findHallById(HallConstants.NOT_EXISTING_DB_ID);
    }


    @Test
    public void whenFindAllByValidLocationId_thenReturnHalls(){
        List<Hall> halls = hallService.findAllById(HallConstants.EXISTING_DB_LOCATION_ID, HallConstants.EXISTING_PAGE, HallConstants.VALID_SIZE);

        //halls on first page
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
    public void whenFindAllByLocationIdNotExistingPage_thenReturnEmptyList(){
        List<Hall> h = hallService.findAllById(HallConstants.EXISTING_DB_LOCATION_ID, HallConstants.NOT_EXISTING_PAGE, HallConstants.VALID_SIZE);
        assertEquals(new ArrayList<>(), h);
    }


    @Test
    @Transactional @Rollback(true)
    public void whenValidAddHall_thenReturnHall(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToAdd = HallConstants.returnNewHall();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);

        List<Hall> allAfter = hallRepository.findAll();

        assertEquals(allBefore.size() + 1, allAfter.size());
        assertNotNull(newHall);
        assertEquals(HallConstants.NEW_NAME, newHall.getName());
        assertEquals(HallConstants.EXISTING_DB_LOCATION_ID, newHall.getLocation().getId());
    }


    @Test(expected = ResourceAlreadyExistsException.class)
    @Transactional @Rollback(true)
    public void whenExistingAddHall_thenReturnResourceAlreadyExistsException(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToAdd = HallConstants.returnExistingHall();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);
        assertEquals(allBefore.size(), hallRepository.findAll().size());
    }


    @Test(expected = ResourceNotFoundException.class)
    @Transactional @Rollback(true)
    public void whenAddHallNotExistingLocation_thenReturnResourceNotFoundException(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToAdd = HallConstants.returnHallOnNotExistingLocation();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);
        assertEquals(allBefore.size(), hallRepository.findAll().size());
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenAddHallEmptyName_thenReturnBadAttributeValueException(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToAdd = HallConstants.returnHallWithEmptyName();
        Hall newHall = hallService.addHall(hallToAdd.getLocationId(), hallToAdd);
        assertEquals(allBefore.size(), hallRepository.findAll().size());
    }



    @Test
    @Transactional @Rollback(true)
    public void whenValidEditHall_thenReturnHall(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToEdit = HallConstants.returnNewHall();
        hallToEdit.setId(HallConstants.EXISTING_DB_ID);
        Hall res = hallService.editHall(hallToEdit);

        assertNotNull(res);
        assertEquals(hallToEdit.getId(), res.getId());
        assertEquals(hallToEdit.getName(), res.getName());
        assertEquals(hallToEdit.getLocationId(), res.getLocation().getId());
        assertEquals(allBefore.size(), hallRepository.findAll().size());
    }


    @Test(expected = ResourceAlreadyExistsException.class)
    @Transactional @Rollback(true)
    public void whenExistingEditHall_thenThrowResourceAlreadyExistsException(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToEdit = HallConstants.returnNewHall();
        hallToEdit.setId(HallConstants.EXISTING_DB_ID);
        hallToEdit.setName(HallConstants.ANOTHER_EXISTING_DB_NAME);
        Hall res = hallService.editHall(hallToEdit);
        assertEquals(allBefore.size(), hallRepository.findAll().size());
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenEditHallEmptyName_thenThrowBadAttributeValueException(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToEdit = HallConstants.returnNewHall();
        hallToEdit.setId(HallConstants.EXISTING_DB_ID);
        hallToEdit.setName("");
        Hall res = hallService.editHall(hallToEdit);
        assertEquals(allBefore.size(), hallRepository.findAll().size());
    }


    @Test(expected = ResourceNotFoundException.class)
    @Transactional
    @Rollback(true)
    public void whenEditNotExistingHall_thenThrowResourceNotFoundException(){
        List<Hall> allBefore = hallRepository.findAll();
        HallDTO hallToEdit = HallConstants.returnNewHall();
        Hall res = hallService.editHall(hallToEdit);
        assertEquals(allBefore.size(), hallRepository.findAll().size());
    }



}
