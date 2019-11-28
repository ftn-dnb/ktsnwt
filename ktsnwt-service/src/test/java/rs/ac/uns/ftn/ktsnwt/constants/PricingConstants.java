package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.Pricing;

public class PricingConstants {

    private PricingConstants() {}

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

}
