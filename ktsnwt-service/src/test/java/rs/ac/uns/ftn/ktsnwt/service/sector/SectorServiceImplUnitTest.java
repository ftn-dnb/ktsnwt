package rs.ac.uns.ftn.ktsnwt.service.sector;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.SectorConstants;
import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;
import rs.ac.uns.ftn.ktsnwt.repository.SectorRepository;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SectorServiceImplUnitTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SectorServiceImpl sectorService;

    @MockBean
    private SectorRepository sectorRepository;

    @MockBean
    private HallRepository hallRepository;


    @Before
    public void setUp(){
        //preparing data
        Address address = new Address(1L, "12", "Sutjeska", "2", "Novi Sad", "Srbija", "21000", 12.0, 12.0);
        Location location = new Location(1L, "SPENS", address, new HashSet<>());
        Hall hall1 = new Hall(1L, "Mala sala", new HashSet<>(), location);
        Hall hall2 = new Hall(2L, "Velika sala", new HashSet<>(), location);
        location.getHalls().add(hall1);
        location.getHalls().add(hall2);
        Sector sector1 = new Sector(1L, "Sedista", 2, 2, 4, SectorType.SEATS, hall1);
        Sector sector2 = new Sector(2L, "Parter", 0, 0, 5, SectorType.FLOOR, hall1);
        Sector sector3 = new Sector(3L, "Parter", 0, 0, 5, SectorType.FLOOR, hall2);
        hall1.getSectors().add(sector1);
        hall1.getSectors().add(sector2);
        hall2.getSectors().add(sector3);

        List<Sector> allInHall = new ArrayList<>();
        allInHall.add(sector1); allInHall.add(sector2);

        Sector newSector = new Sector();

        //mocking
        Mockito.when(hallRepository.findById(SectorConstants.EXISTING_DB_HALL_ID)).thenReturn(Optional.of(hall1));
        Mockito.when(hallRepository.findById(SectorConstants.NEW_VALID_HALL_ID)).thenReturn(Optional.of(hall2));

        Mockito.when(sectorRepository.getByHallId(SectorConstants.EXISTING_DB_HALL_ID, PageRequest.of(SectorConstants.NOT_EXISTING_PAGE, SectorConstants.VALID_SIZE))).thenReturn(new PageImpl<>(new ArrayList<>()));
        Mockito.when(sectorRepository.getByHallId(SectorConstants.NOT_EXISTING_HALL_ID, PageRequest.of(SectorConstants.EXISTING_PAGE, SectorConstants.VALID_SIZE))).thenReturn(new PageImpl<>(new ArrayList<>()));
        Mockito.when(sectorRepository.getByHallId(SectorConstants.EXISTING_DB_HALL_ID, PageRequest.of(SectorConstants.EXISTING_PAGE, SectorConstants.VALID_SIZE))).thenReturn(new PageImpl<>(allInHall));
        Mockito.when(sectorRepository.findById(SectorConstants.EXISTING_DB_ID)).thenReturn(Optional.of(new Sector(1L, "Sedista", 2, 2, 4, SectorType.SEATS, new Hall(1L, "", null, null))));
        Mockito.when(sectorRepository.findById(SectorConstants.NOT_EXISTING_DB_ID)).thenReturn(Optional.empty());
        Mockito.when(sectorRepository.findByName(SectorConstants.NEW_VALID_NAME, SectorConstants.NEW_VALID_HALL_ID)).thenReturn(null);
        Mockito.when(sectorRepository.findByName(SectorConstants.EXISTING_NAME, SectorConstants.NEW_VALID_HALL_ID)).thenReturn(new Sector());
        Mockito.when(hallRepository.findById(SectorConstants.NOT_EXISTING_HALL_ID)).thenReturn(null);
        Mockito.when(sectorRepository.save(any(Sector.class))).thenReturn(newSector);
        Mockito.when(sectorRepository.findByName(SectorConstants.ANOTHER_EXISTING_NAME, SectorConstants.EXISTING_DB_HALL_ID)).thenReturn(new Sector());

    }



    @Test
    public void whenFindByValidId_thenReturnSector(){
        Sector s = sectorService.findById(SectorConstants.EXISTING_DB_ID);

        assertEquals(s.getId(), SectorConstants.EXISTING_DB_ID);
        assertEquals(s.getName(), SectorConstants.ANOTHER_EXISTING_NAME);
        assertEquals(s.getHall().getId(), SectorConstants.EXISTING_DB_HALL_ID );
        assertEquals(s.getCapacity(), SectorConstants.ORIGINAL_CAPACITY);
        assertEquals(s.getType(), SectorType.SEATS);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByInvalidId_thenThrowResourceNotFoundException(){
        sectorService.findById(SectorConstants.NOT_EXISTING_DB_ID);
    }

    @Test
    public void whenFindAllByValidHallId_thenReturnSectors(){
        List<Sector> all = sectorService.findAllById(SectorConstants.EXISTING_DB_HALL_ID, SectorConstants.EXISTING_PAGE, SectorConstants.VALID_SIZE);

        for(int i = 1; i <= all.size(); i += 1){
            long j = i;
            assertTrue(j == all.get(i-1).getId());
        }
        assertTrue(all.size() <= SectorConstants.VALID_SIZE);
    }

    @Test(expected = Exception.class)   //mozda i ne mora da baca exc, ali da se na drugi nacin hendluje
    public void whenFindAllByInvalidHallId_thenReturnEmptyList(){
        List<Sector> all = sectorService.findAllById(SectorConstants.NOT_EXISTING_HALL_ID, SectorConstants.EXISTING_PAGE, SectorConstants.VALID_SIZE);
    }


    @Test
    public void whenGetAllSectorsOnNotExistingPage(){
        List<Sector> all = sectorService.findAllById(SectorConstants.EXISTING_DB_HALL_ID, SectorConstants.NOT_EXISTING_PAGE, SectorConstants.VALID_SIZE);
        assertEquals(all, new ArrayList<Sector>());
    }



    @Test
    public void whenValidAddSector_thenReturnAddedSector(){
        SectorDTO s = SectorConstants.createNewSectorDTO();
        Sector sNew = sectorService.addSector(s);

        Mockito.verify(sectorRepository).save(any(Sector.class));
        assertEquals(SectorType.SEATS, sNew.getType());
        assertEquals(SectorConstants.NEW_VALID_CAPACITY, sNew.getCapacity());
        assertEquals(SectorConstants.NEW_VALID_COLUMNS, sNew.getNumColumns());
        assertEquals(SectorConstants.NEW_VALID_ROWS, sNew.getNumRows());
        assertEquals(SectorConstants.NEW_VALID_HALL_ID, sNew.getHall().getId());
        assertEquals(SectorConstants.NEW_VALID_NAME, sNew.getName());
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenAddSectorEmptyName(){
        SectorDTO s = SectorConstants.createNewSectorDTO();
        s.setName("");
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = ResourceAlreadyExistsException.class)
    public void whenAddSectorExistingNameAndHallId(){
        SectorDTO s = SectorConstants.createExistingSectorDTO();
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void whenAddSectorNonExistingHallId(){
        SectorDTO s = SectorConstants.createSectorDTOWithNotExistingHallId();
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenAddSectorWithInvalidCalculatingCapacity(){
        SectorDTO s = SectorConstants.createSectorDTOInvalidCalculatingCapacity();
        Sector sNew = sectorService.addSector(s);
    }

    @Test(expected = BadAttributeValueException.class)
    public void whenAddSectorWithInvalidCapacity(){
        SectorDTO s = SectorConstants.createSectorDTOInvalidCapacity();
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenAddSeatSectorWithInvalidRows(){
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidRows();
        Sector sNew = sectorService.addSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenAddSeatSectorWithInvalidColumns(){
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidColumns();
        Sector sNew = sectorService.addSector(s);
    }

    @Test
    public void whenValidEditSector_thenReturnEditedSector(){
        SectorDTO s = SectorConstants.createNewSectorDTO();
        s.setName(SectorConstants.RANDOM_NAME);
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector sEdit = sectorService.editSector(s);
        assertEquals(SectorConstants.RANDOM_NAME, sEdit.getName());
        assertEquals(SectorConstants.NEW_VALID_CAPACITY, sEdit.getCapacity());
        assertEquals(SectorConstants.NEW_VALID_ROWS, sEdit.getNumRows());
        assertEquals(SectorConstants.NEW_VALID_COLUMNS, sEdit.getNumColumns());
        Mockito.verify(sectorRepository).save(any(Sector.class));

    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void whenEditToExistingSector(){
        SectorDTO s = SectorConstants.createExistingSectorDTO();
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector sEdit = sectorService.editSector(s);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenEditWithNotExistingHallId(){
        SectorDTO s = SectorConstants.createSectorDTOWithNotExistingHallId();
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector sEdit = sectorService.editSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenEditSectorWithInvalidCapacity(){
        SectorDTO s = SectorConstants.createSectorDTOInvalidCapacity();
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector sEdit = sectorService.editSector(s);
    }

    @Test(expected = BadAttributeValueException.class)
    public void whenEditSectorWithInvalidCalculatedCapacity(){
        SectorDTO s = SectorConstants.createSectorDTOInvalidCalculatingCapacity();
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector sEdit = sectorService.editSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenEditSectorWithInvalidRows(){
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidRows();
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector sEdit = sectorService.editSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenEditSectorWithInvalidColumns(){
        SectorDTO s = SectorConstants.createSeatSectorDTOInvalidColumns();
        s.setId(SectorConstants.EXISTING_DB_ID);
        Sector sEdit = sectorService.editSector(s);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenEditSectorEmptyName(){
        SectorDTO s = SectorConstants.createExistingSectorDTO();
        s.setId(SectorConstants.EXISTING_DB_ID);
        s.setName("");
        Sector sEdit = sectorService.editSector(s);
    }

}
