package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import java.util.ArrayList;
import java.util.List;

public class PricingConstants {

    public static final int DB_NUM_PRICINGS = 2;

    // Existing elements
    public static final Long DB_ID_1 = 1L;
    public static final double DB_PRICE_1 = 199;
    public static final Long DB_REFERENCED_EVENT_DAY_ID_1 = 1L;
    public static final Long DB_REFERENCED_SECTOR_ID_1 = 1L;

    public static final Long DB_ID_2 = 2L;
    public static final double DB_PRICE_2 = 399;
    public static final Long DB_REFERENCED_EVENT_DAY_ID_2 = 1L;
    public static final Long DB_REFERENCED_SECTOR_ID_2 = 2L;

    // Mocking values
    public static final int NUM_MOCKED_PRICINGS = 2;
    public static final Long MOCK_ID_1 = 9999L;
    public static final Long MOCK_ID_2 = 99999L;

    // Non existing
    public static final Long NON_EXISTING_DB_ID = 123456L;
    
    public static final Long DB_ID = 1L;
    public static final double DB_PRICE = 1200;

    public static Pricing returnMockedPricing() {
        Pricing pricing = new Pricing();
        pricing.setId(PricingConstants.DB_ID);
        pricing.setPrice(PricingConstants.DB_PRICE);
        pricing.setSector(SectorConstants.returnMockedSector());
        pricing.setEventDay(EventDayConstants.returnMockedEventDay());
        return pricing;
    }

    public static List<Pricing> createPricingListForMocking() {
        Pricing pricing1 = new Pricing();
        pricing1.setId(MOCK_ID_1);
        Pricing pricing2 = new Pricing();
        pricing2.setId(MOCK_ID_2);

        List<Pricing> pricings = new ArrayList<>();
        pricings.add(pricing1);
        pricings.add(pricing2);

        return pricings;
    }

    private PricingConstants() {
    }
}
