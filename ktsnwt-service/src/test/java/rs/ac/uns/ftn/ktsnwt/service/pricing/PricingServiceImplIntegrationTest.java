package rs.ac.uns.ftn.ktsnwt.service.pricing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.PricingConstants;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceImplIntegrationTest {

    @Autowired
    private PricingServiceImpl pricingService;

    @Test
    public void whenFindAllReturnAllElements() {
        List<Pricing> pricings = pricingService.findAll();
        assertEquals(PricingConstants.DB_NUM_PRICINGS, pricings.size());
        assertEquals(PricingConstants.DB_ID_1, pricings.get(0).getId());
        assertEquals(PricingConstants.DB_ID_2, pricings.get(1).getId());
    }

    @Test
    public void whenGetPricingReturnPricing() {
        Pricing pricing = pricingService.getPricing(PricingConstants.DB_ID_1);
        assertNotNull(pricing);
        assertEquals(PricingConstants.DB_ID_1, pricing.getId());
        assertEquals(PricingConstants.DB_REFERENCED_EVENT_DAY_ID_1, pricing.getEventDay().getId());
        assertEquals(PricingConstants.DB_REFERENCED_SECTOR_ID_1, pricing.getSector().getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenGetPricingThrowResourceNotFound() {
        Pricing pricing = pricingService.getPricing(PricingConstants.NON_EXISTING_DB_ID);
    }
}
