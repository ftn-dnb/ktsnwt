package rs.ac.uns.ftn.ktsnwt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.LocationConstants;
import rs.ac.uns.ftn.ktsnwt.model.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationRepositoryIntegrationTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void whenFindByNameReturnLocation() {
        Location location = locationRepository.findByName(LocationConstants.EXISTING_DB_NAME);
        assertEquals(LocationConstants.DB_ID, location.getId());
        assertEquals(LocationConstants.EXISTING_DB_NAME, location.getName());
    }

    @Test
    public void whenFindByNameReturnEmpty() {
        Location location = locationRepository.findByName(LocationConstants.NOT_EXISTING_DB_NAME);
        assertNull(location);
    }

}
