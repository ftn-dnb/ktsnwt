package rs.ac.uns.ftn.ktsnwt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.AddressConstants;
import rs.ac.uns.ftn.ktsnwt.model.Address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepositoryIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void whenFindByGoogleApiIdReturnAddress() {
        Address address = addressRepository.findByGoogleApiId(AddressConstants.DB_GOOGLE_API_ID);
        assertEquals(AddressConstants.DB_GOOGLE_API_ID, address.getGoogleApiId());
    }

    @Test
    public void whenFindByGoogleApiIdReturnNull() {
        Address address = addressRepository.findByGoogleApiId(AddressConstants.NON_EXISTING_DB_GOOGLE_API_ID);
        assertNull(address);
    }
}
