package rs.ac.uns.ftn.ktsnwt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.HallConstants;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HallRepositoryIntegrationTest {

    @Autowired
    HallRepository hallRepository;



    @Test
    public void whenFindByExistingName_thenReturnHall(){
        Hall h = hallRepository.findByName(HallConstants.EXISTING_DB_NAME, HallConstants.EXISTING_DB_LOCATION_ID);
        assertNotNull(h);
        assertEquals(HallConstants.EXISTING_DB_NAME, h.getName());
        assertTrue(HallConstants.EXISTING_DB_LOCATION_ID ==h.getLocation().getId());
    }

    @Test
    public void whenFindByNotExistingName_thenReturnEmpty(){
        Hall h = hallRepository.findByName(HallConstants.NOT_EXISTING_DB_NAME, HallConstants.EXISTING_DB_LOCATION_ID);
        assertNull(h);
    }

    @Test
    public void whenFindByNotExistingLocationId_thenReturnEmpty(){
        Hall h = hallRepository.findByName(HallConstants.EXISTING_DB_NAME, HallConstants.NOT_EXISTING_DB_LOCATION_ID);
        assertNull(h);
    }

}
