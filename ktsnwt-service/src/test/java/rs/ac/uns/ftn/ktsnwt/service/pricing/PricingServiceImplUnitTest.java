package rs.ac.uns.ftn.ktsnwt.service.pricing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.PricingConstants;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.repository.PricingRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceImplUnitTest {

    @Autowired
    private PricingServiceImpl pricingService;

    @MockBean
    private PricingRepository pricingRepository;

    @Before
    public void setUp() {
        List<Pricing> pricings = PricingConstants.createPricingListForMocking();
        Mockito.when(pricingRepository.findAll()).thenReturn(pricings);

        Pricing mockPricing = new Pricing();
        mockPricing.setId(PricingConstants.MOCK_ID_1);
        Mockito.when(pricingRepository.findById(PricingConstants.MOCK_ID_1)).thenReturn(Optional.of(mockPricing));
    }

    @Test
    public void whenFindAllReturnAllElements() {
        List<Pricing> pricings = pricingService.findAll();
        assertEquals(PricingConstants.NUM_MOCKED_PRICINGS, pricings.size());
        assertEquals(PricingConstants.MOCK_ID_1, pricings.get(0).getId());
        assertEquals(PricingConstants.MOCK_ID_2, pricings.get(1).getId());
    }

    @Test
    public void whenGetPricingReturnPricing() {
        Pricing pricing = pricingService.getPricing(PricingConstants.MOCK_ID_1);
        assertNotNull(pricing);
        assertEquals(PricingConstants.MOCK_ID_1, pricing.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenGetPricingThrowResourceNotFoundException() {
        Pricing pricing = pricingService.getPricing(PricingConstants.NON_EXISTING_DB_ID);
    }
}
