package rs.ac.uns.ftn.ktsnwt.service.location;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.ktsnwt.constants.AddressConstants;
import rs.ac.uns.ftn.ktsnwt.constants.LocationConstants;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.repository.LocationRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationServiceImplIntegrationTest {

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private LocationRepository locationRepository;



    @Test
    public void whenFindAll_thenReturnAllLocations(){
        List<Location> all = locationService.findAll();
        assertEquals(1,all.size());
        assertEquals(LocationConstants.DB_ID, all.get(0).getId());
        assertEquals(LocationConstants.EXISTING_DB_NAME, all.get(0).getName());
    }

    @Test
    public void whenFindByValidId_thenReturnLocation(){
        Location l = locationService.findById(LocationConstants.DB_ID);
        assertNotNull(l);
        assertNotNull(l.getId());
        assertEquals(LocationConstants.DB_ID, l.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByInvalidId_thenThrowResourceNotFoundException(){
        locationService.findById(LocationConstants.NOT_EXISTING_DB_ID);
    }

    @Test
    public void findAllOnPage_thenReturnAllOnPage(){
        List<Location> all = locationService.findAll(LocationConstants.VALID_PAGE, LocationConstants.VALID_SIZE);
        assertEquals(1,all.size());
        assertEquals(LocationConstants.DB_ID, all.get(0).getId());
        assertEquals(LocationConstants.EXISTING_DB_NAME, all.get(0).getName());
    }

    @Test
    public void whenFindAllOnNotExistingPage_thenReturnEmptyList(){
        List<Location> all = locationService.findAll(LocationConstants.NOT_EXISTING_PAGE, LocationConstants.VALID_SIZE);
        assertEquals(0,all.size());
    }

    @Test
    public void whenFindByExistingName_thenReturnLocation(){
        Location l = locationService.findByName(LocationConstants.EXISTING_DB_NAME);
        assertEquals(LocationConstants.EXISTING_DB_NAME, l.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Ignore
    public void whenFindByNotExistingName_thenThrowResourceNotFoundException(){
        locationService.findByName(LocationConstants.NOT_EXISTING_DB_NAME);
    }


    @Test
    @Transactional
    @Rollback(true)
    public void whenAddUniqueLocation_thenReturnLocation(){
        List<Location> allBefore = locationRepository.findAll();
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        Location newL = locationService.addLocation(l);
        List<Location> allAfter = locationRepository.findAll();

        assertNotNull(newL);
        assertEquals(l.getName(), newL.getName());
        assertEquals(l.getAddress().getId(), newL.getAddress().getId());
        assertEquals(allBefore.size()+1, allAfter.size());
    }

    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    @Ignore
    public void whenAddLocationEmptyName_thenThrowBadAttributeValueException(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.setName("");
        locationService.addLocation(l);
    }

    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    @Ignore
    public void whenAddLocationNotFilledAddress_thenThrowBadAttributeValueException(){
        LocationDTO l = LocationConstants.returnNewLocationDTONotFilledAddress();
        locationService.addLocation(l);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenAddLocationNotExistingAddress_thenReturnLocation(){
        List<Location> allBefore = locationRepository.findAll();
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.getAddress().setGoogleApiId(AddressConstants.NON_EXISTING_DB_GOOGLE_API_ID);

        Location newL = locationService.addLocation(l);
        List<Location> allAfter = locationRepository.findAll();
        assertEquals(l.getAddress().getGoogleApiId(), newL.getAddress().getGoogleApiId());
        assertEquals(l.getName(), newL.getName());
        assertEquals(allBefore.size()+1, allAfter.size());
    }


    @Test(expected = ResourceAlreadyExistsException.class)
    @Transactional @Rollback(true)
    public void whenAddExistingLocation_thenThrowResourceAlreadyExistsException(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.setName(LocationConstants.EXISTING_DB_NAME);
        l.getAddress().setGoogleApiId(AddressConstants.DB_GOOGLE_API_ID);

        locationService.addLocation(l);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenEditToUniqueLocation_thenReturnLocation(){
        List<Location> all = locationRepository.findAll();
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.setId(LocationConstants.DB_ID);
        l.setName(LocationConstants.NOT_EXISTING_DB_NAME);

        Location editedL = locationService.editLocation(l);
        assertNotNull(editedL);
        assertEquals(editedL.getId(), l.getId());
        assertEquals(editedL.getAddress().getGoogleApiId(), l.getAddress().getGoogleApiId());
        assertEquals(editedL.getName(), l.getName());
        assertEquals(all.size(), locationRepository.findAll().size());
    }

    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    @Ignore
    public void whenEditLocationEmptyName_thenThrowBadAttributeValueException(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setName("");
        locationService.editLocation(l);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    @Transactional @Rollback(true)
    public void whenEditToExistingLocation_thenThrowResourceAlreadyExistsException(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        locationService.editLocation(l);
    }


    @Test(expected = NoSuchElementException.class)
    @Transactional @Rollback(true)
    public void whenEditNotExistingLocation_thenThrowResourceNotFoundException(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setId(LocationConstants.NOT_EXISTING_DB_ID);
        l.setName(LocationConstants.NOT_EXISTING_DB_NAME);
        locationService.editLocation(l);
    }


    @Test(expected = BadAttributeValueException.class)
    @Transactional @Rollback(true)
    @Ignore
    public void whenChangeAddressToNotValid_thenThrowBadAttributeValueException(){
        Long id = LocationConstants.DB_ID;
        AddressDTO a = AddressConstants.createNewAddressDto();
        a.setGoogleApiId(null);
        a.setStreetName("");
        locationService.changeAddress(id, a);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenAddressChangeToValid_thenReturnLocation(){
        Long id = LocationConstants.DB_ID;
        AddressDTO a = AddressConstants.createNewAddressDto();
        List<Location> all = locationRepository.findAll();

        Location changedL = locationService.changeAddress(id, a);

        assertNotNull(changedL);
        assertEquals(changedL.getAddress().getGoogleApiId(), a.getGoogleApiId());
        assertEquals(id, changedL.getId());
        assertEquals(all.size(), locationRepository.findAll().size());

    }


    @Test(expected = NoSuchElementException.class)
    @Transactional @Rollback(true)
    public void whenChangeAddressOfNotExistingLocation_thenThrowResourceNotFoundException(){
        Long id = LocationConstants.NOT_EXISTING_DB_ID;
        AddressDTO a = AddressConstants.createNewAddressDto();

        locationService.changeAddress(id, a);
    }

}
