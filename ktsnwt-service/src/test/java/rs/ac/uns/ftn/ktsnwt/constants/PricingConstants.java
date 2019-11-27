package rs.ac.uns.ftn.ktsnwt.constants;

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

    // Non existing
    public static final Long NON_EXISTING_DB_ID = 123456L;

    private PricingConstants() {
    }
}
