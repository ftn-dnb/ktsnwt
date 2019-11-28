package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.Hall;

public class HallConstants {

    private HallConstants() {
    }

    public static final Long MOCK_ID = 1L;

    public static Hall returnMockedHall() {
        Hall hall = new Hall();
        hall.setId(HallConstants.MOCK_ID);
        hall.setLocation(LocationConstants.returnMockedLocation());
        return hall;
    }

}
