package rs.ac.uns.ftn.ktsnwt.service.sector;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.omg.CORBA.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import rs.ac.uns.ftn.ktsnwt.constants.SectorConstants;
import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.mappers.SectorMapper;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;
import rs.ac.uns.ftn.ktsnwt.repository.SectorRepository;

//import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SectorServiceImplIntegrationTest {
    @Autowired
    private SectorService sectorService;

    @Autowired
    private SectorRepository sectorRepository;

    @Test
    public void whenFindByValidId_thenReturnSector(){
        Long id = SectorConstants.EXISTING_DB_ID;
        Sector s = sectorService.findById(id);

        assertEquals(s.getId(), id);
        assertEquals(s.getName(), SectorConstants.ANOTHER_EXISTING_NAME);
        assertEquals(s.getHall().getId(), SectorConstants.EXISTING_DB_HALL_ID );
        assertEquals(s.getCapacity(), SectorConstants.ORIGINAL_CAPACITY);
        assertEquals(s.getType(), SectorType.SEATS);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByInvalidId_thenThrowResourceNotFoundException(){
        Long id = SectorConstants.NOT_EXISTING_DB_ID;
        Sector s = sectorService.findById(id);
    }

    @Test
    public void whenFindAllByValidHallId_thenReturnSectors(){
        List<Sector> all = sectorService.findAllById(SectorConstants.EXISTING_DB_HALL_ID, SectorConstants.EXISTING_PAGE, SectorConstants.VALID_SIZE);
        for(int i = 1; i <= all.size(); i += 1){
            long j = i;
            assertTrue(j == all.get(i-1).getId());
        }
        assertTrue( all.size() <= SectorConstants.VALID_SIZE);
    }

    @Test
    public void whenFindAllByInvalidHallId_thenReturnEmptyList(){
        List<Sector> all = sectorService.findAllById(SectorConstants.NOT_EXISTING_HALL_ID, SectorConstants.EXISTING_PAGE, SectorConstants.VALID_SIZE);
        assertEquals(all, new ArrayList<Sector>());
    }

    @Test
    public void whenGetAllSectorsOnNotExistingPage(){
        List<Sector> all = sectorService.findAllById(SectorConstants.EXISTING_DB_HALL_ID, SectorConstants.NOT_EXISTING_PAGE, SectorConstants.VALID_SIZE);
        assertEquals(all, new ArrayList<Sector>());
    }

    @Test
    @Transactional @Rollback(true)
    public void whenValidAddSector_thenReturnAddedSector(){
        List<Sector> allBefore = sectorRepository.findAll();
        Sector s = sectorService.addSector(SectorConstants.createNewSectorDTO());
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter.size(), allBefore.size()+1);
        assertEquals(SectorConstants.NEW_VALID_COLUMNS, s.getNumColumns());
        assertEquals(SectorConstants.NEW_VALID_ROWS, s.getNumRows());
        assertEquals(SectorConstants.NEW_VALID_HALL_ID, s.getHall().getId());
        assertEquals(SectorConstants.NEW_VALID_CAPACITY, s.getCapacity());
        assertEquals(SectorConstants.NEW_VALID_NAME, s.getName());

    }


    @Test(expected = ResourceAlreadyExistsException.class)
    @Transactional @Rollback(true)
    public void whenAddSectorExistingNameAndHallId(){
        SectorDTO s = SectorConstants.createExistingSectorDTO();
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = ResourceNotFoundException.class)
    @Transactional @Rollback(true)
    public void whenAddSectorNonExistingHallId(){
        SectorDTO s = SectorConstants.createSectorDTOWithNotExistingHallId();
        Sector sNew = sectorService.addSector(s);
    }



    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenAddSectorWithInvalidCalculatingCapacity(){
        SectorDTO s = SectorConstants.createSectorDTOInvalidCalculatingCapacity();
        Sector sNew = sectorService.addSector(s);
    }

    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback()
    public void whenAddSectorWithInvalidCapacity(){
        SectorDTO s = SectorConstants.createSectorDTOInvalidCapacity();
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenAddSeatSectorWithInvalidRows(){
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidRows();
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenAddSeatSectorWithInvalidColumns(){
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidColumns();
        Sector sNew = sectorService.addSector(s);
    }

    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenAddSeatSectorEmptyName(){
        SectorDTO s = SectorConstants.createNewSectorDTO();
        s.setName("");
        Sector sNew = sectorService.addSector(s);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenValidEditSector_thenReturnEditedSector(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createNewSectorDTO();
        s.setName(SectorConstants.RANDOM_NAME);
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter.size(), allBefore.size());
        assertEquals(SectorConstants.RANDOM_NAME, edited.getName());
        assertEquals(SectorConstants.NEW_VALID_CAPACITY, edited.getCapacity());
        assertEquals(SectorConstants.NEW_VALID_ROWS, edited.getNumRows());
        assertEquals(SectorConstants.NEW_VALID_COLUMNS, edited.getNumColumns());
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    @Transactional @Rollback(true)
    public void whenEditToExistingSector(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createExistingSectorDTO();
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter, allBefore);
    }

    @Test(expected = ResourceNotFoundException.class)
    @Transactional @Rollback(true)
    public void whenEditWithNotExistingHallId(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createSectorDTOWithNotExistingHallId();
        s.setId(SectorConstants.EXISTING_DB_ID);
        s.setName(SectorConstants.RANDOM_NAME);
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter, allBefore);
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenEditSectorWithInvalidCapacity(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createSectorDTOInvalidCapacity();
        s.setId(SectorConstants.EXISTING_DB_ID);
        s.setName(SectorConstants.RANDOM_NAME);
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter, allBefore);
    }

    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenEditSectorWithInvalidCalculatedCapacity(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createSectorDTOInvalidCalculatingCapacity();
        s.setId(SectorConstants.EXISTING_DB_ID);
        s.setName(SectorConstants.RANDOM_NAME);
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter, allBefore);
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenEditSectorWithInvalidRows(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidRows();
        s.setId(SectorConstants.EXISTING_DB_ID);
        s.setName(SectorConstants.RANDOM_NAME);
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter, allBefore);
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenEditSectorWithInvalidColumns(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidColumns();
        s.setId(SectorConstants.EXISTING_DB_ID);
        s.setName(SectorConstants.RANDOM_NAME);
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter, allBefore);

    }

    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    public void whenEditSectorEmptyName(){
        List<Sector> allBefore = sectorRepository.findAll();
        SectorDTO s = SectorConstants.createExistingSectorDTO();
        s.setId(SectorConstants.EXISTING_DB_ID);
        s.setName("");
        Sector edited = sectorService.editSector(s);
        List<Sector> allAfter = sectorRepository.findAll();
        assertEquals(allAfter, allBefore);

    }



}
