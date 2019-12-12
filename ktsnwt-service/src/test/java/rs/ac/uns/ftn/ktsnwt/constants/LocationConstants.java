package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.Location;

public class LocationConstants {

    private LocationConstants() {}

    public static final Long MOCK_ID = 1L;
    public static final String EXISTING_DB_NAME = "SPENS";

    public static final Long DB_ID = 1L;

    public static Location returnMockedLocation() {
        Location location = new Location();
        location.setId(LocationConstants.MOCK_ID);
        return location;
    }
}
