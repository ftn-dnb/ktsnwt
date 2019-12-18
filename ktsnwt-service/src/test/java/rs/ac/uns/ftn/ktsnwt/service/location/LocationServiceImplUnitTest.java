package rs.ac.uns.ftn.ktsnwt.service.location;


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
import rs.ac.uns.ftn.ktsnwt.constants.AddressConstants;
import rs.ac.uns.ftn.ktsnwt.constants.LocationConstants;
import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.repository.AddressRepository;
import rs.ac.uns.ftn.ktsnwt.repository.LocationRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationServiceImplUnitTest {

    @MockBean
    private LocationRepository locationRepository;

    @Autowired
    private LocationServiceImpl locationService;

    @MockBean
    private AddressRepository addressRepository;


    @Before
    public void setUp(){
        Address a = new Address(AddressConstants.DB_ID, AddressConstants.DB_GOOGLE_API_ID, AddressConstants.DB_STREET_NAME, AddressConstants.DB_STREET_NUMBER, AddressConstants.DB_CITY, AddressConstants.DB_COUNTRY, AddressConstants.DB_POSTAL_CODE, AddressConstants.DB_LATITUDE, AddressConstants.DB_LONGITUDE);
        AddressDTO newA = AddressConstants.createNewAddressDto();
        Location existLocation = new Location(LocationConstants.DB_ID, LocationConstants.EXISTING_DB_NAME, a, new HashSet<>());
        List<Location> all = new ArrayList<>();
        all.add(existLocation);

        Mockito.when(locationRepository.findAll()).thenReturn(all);

        Mockito.when(locationRepository.findById(LocationConstants.DB_ID)).thenReturn(java.util.Optional.of(existLocation));
        Mockito.when(locationRepository.findById(LocationConstants.NOT_EXISTING_DB_ID)).thenReturn(Optional.empty());

        Mockito.when(locationRepository.findAll(PageRequest.of(LocationConstants.VALID_PAGE, LocationConstants.VALID_SIZE))).thenReturn(new PageImpl<>(all));
        Mockito.when(locationRepository.findAll(PageRequest.of(LocationConstants.NOT_EXISTING_PAGE, LocationConstants.VALID_SIZE))).thenReturn(new PageImpl<>(new ArrayList<>()));

        Mockito.when(locationRepository.findByName(LocationConstants.EXISTING_DB_NAME)).thenReturn(existLocation);
        Mockito.when(locationRepository.findByName(LocationConstants.NOT_EXISTING_DB_NAME)).thenReturn(null);
        Mockito.when(locationRepository.findByName(LocationConstants.NEW_DB_NAME)).thenReturn(null);
        Mockito.when(locationRepository.findByName("")).thenReturn(null);

        Mockito.when(locationRepository.save(any(Location.class))).thenReturn(new Location());

        Mockito.when(addressRepository.findByGoogleApiId(AddressConstants.DB_GOOGLE_API_ID)).thenReturn(a);
        Mockito.when(addressRepository.findByGoogleApiId(null)).thenReturn(null);
        Mockito.when(addressRepository.findByGoogleApiId(AddressConstants.NON_EXISTING_DB_GOOGLE_API_ID)).thenReturn(null);
        Mockito.when(addressRepository.findByGoogleApiId(AddressConstants.NEW_DB_GOOGLE_API_ID)).thenReturn(null);

        Mockito.when(addressRepository.save(any(Address.class))).thenReturn(new Address());

    }

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
    public void whenFindByNotExistingName_thenThrowResourceNotFoundException(){
        locationService.findByName(LocationConstants.NOT_EXISTING_DB_NAME);
    }


    @Test
    public void whenAddUniqueLocation_thenReturnLocation(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        Location newL = locationService.addLocation(l);

        assertNotNull(newL);
        assertEquals(l.getName(), newL.getName());
        assertEquals(l.getAddress().getId(), newL.getAddress().getId());

    }

    @Test(expected = BadAttributeValueException.class)
    public void whenAddLocationEmptyName_thenThrowBadAttributeValueException(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.setName("");
        locationService.addLocation(l);
    }

    @Test(expected = BadAttributeValueException.class)
    public void whenAddLocationNotFilledAddress_thenThrowBadAttributeValueException(){
        LocationDTO l = LocationConstants.returnNewLocationDTONotFilledAddress();
        locationService.addLocation(l);
    }

    @Test
    public void whenAddLocationNotExistingAddress_thenReturnLocation(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.getAddress().setGoogleApiId(AddressConstants.NON_EXISTING_DB_GOOGLE_API_ID);

        Location newL = locationService.addLocation(l);
        assertEquals(l.getAddress().getGoogleApiId(), newL.getAddress().getGoogleApiId());
        assertEquals(l.getName(), newL.getName());
    }


    @Test(expected = ResourceAlreadyExistsException.class)
    public void whenAddExistingLocation_thenThrowResourceAlreadyExistsException(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.setName(LocationConstants.EXISTING_DB_NAME);
        l.getAddress().setGoogleApiId(AddressConstants.DB_GOOGLE_API_ID);

        locationService.addLocation(l);
    }

    @Test
    public void whenEditToUniqueLocation_thenReturnLocation(){
        LocationDTO l = LocationConstants.returnNewLocationDTO();
        l.setId(LocationConstants.DB_ID);
        l.setName(LocationConstants.NOT_EXISTING_DB_NAME);

        Location editedL = locationService.editLocation(l);
        assertNotNull(editedL);
        assertEquals(editedL.getId(), l.getId());
        assertEquals(editedL.getAddress().getGoogleApiId(), l.getAddress().getGoogleApiId());
        assertEquals(editedL.getName(), l.getName());
    }

    @Test(expected = BadAttributeValueException.class)
    public void whenEditLocationEmptyName_thenThrowBadAttributeValueException(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setName("");
        locationService.editLocation(l);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void whenEditToExistingLocation_thenThrowResourceAlreadyExistsException(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        locationService.editLocation(l);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void whenEditNotExistingLocation_thenThrowResourceNotFoundException(){
        LocationDTO l = LocationConstants.returnDBLocationDTO();
        l.setId(LocationConstants.NOT_EXISTING_DB_ID);
        l.setName(LocationConstants.NOT_EXISTING_DB_NAME);
        locationService.editLocation(l);
    }


    @Test(expected = BadAttributeValueException.class)
    public void whenChangeAddressToNotValid_thenThrowBadAttributeValueException(){
        Long id = LocationConstants.DB_ID;
        AddressDTO a = AddressConstants.createNewAddressDto();
        a.setGoogleApiId(null);
        a.setStreetName("");
        locationService.changeAddress(id, a);
    }

    @Test
    public void whenAddressChangeToValid_thenReturnLocation(){
        Long id = LocationConstants.DB_ID;
        AddressDTO a = AddressConstants.createNewAddressDto();

        Location changedL = locationService.changeAddress(id, a);

        assertNotNull(changedL);
        assertEquals(changedL.getAddress().getGoogleApiId(), a.getGoogleApiId());
        assertEquals(id, changedL.getId());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void whenChangeAddressOfNotExistingLocation_thenThrowResourceNotFoundException(){
        Long id = LocationConstants.NOT_EXISTING_DB_ID;
        AddressDTO a = AddressConstants.createNewAddressDto();

        locationService.changeAddress(id, a);
    }


    @After
    public void tearDown(){

    }
}
