package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Sector;

import java.util.ArrayList;
import java.util.HashSet;

public class HallConstants {

    private HallConstants() {
    }

    //existing - valid
    public static final Long MOCK_ID = 1L;
    public static final String EXISTING_DB_NAME = "Mala sala";
    public static final String ANOTHER_EXISTING_DB_NAME = "Velika sala";
    public static final Long EXISTING_DB_ID = 1L;
    public static final Long EXISTING_DB_LOCATION_ID = 1L;
    public static final int EXISTING_PAGE = 0;
    public static final int VALID_SIZE = 5;

    //not existing - invalid
    public static final Long NOT_EXISTING_DB_ID = 123L;
    public static final String NOT_EXISTING_DB_NAME = "AA";
    public static final Long NOT_EXISTING_DB_LOCATION_ID = 123L;
    public static final int NOT_VALID_SIZE = -1;
    public static final int NOT_VALID_PAGE = -1;
    public static final int NOT_EXISTING_PAGE = 1234;


    //new
    public static final String NEW_NAME = "hall1";
    public static final Long NEXT_DB_ID = 3L;



    public static Hall returnMockedHall() {
        Hall hall = new Hall();
        hall.setId(HallConstants.MOCK_ID);
        hall.setLocation(LocationConstants.returnMockedLocation());
        return hall;
    }

    public static HallDTO returnNewHall() {
        HallDTO hall = new HallDTO();
        hall.setId(HallConstants.NEXT_DB_ID);
        hall.setLocationId(HallConstants.EXISTING_DB_LOCATION_ID);
        hall.setName(HallConstants.NEW_NAME);
        hall.setSectors(new ArrayList<>());
        return hall;
    }

    public static HallDTO returnExistingHall() {
        HallDTO hall = new HallDTO();
        hall.setId(HallConstants.EXISTING_DB_ID);
        hall.setLocationId(HallConstants.EXISTING_DB_LOCATION_ID);
        hall.setName(HallConstants.EXISTING_DB_NAME);
        hall.setSectors(new ArrayList<>());
        return hall;
    }

    public static HallDTO returnHallOnNotExistingLocation() {
        HallDTO hall = new HallDTO();
        hall.setId(HallConstants.NEXT_DB_ID);
        hall.setLocationId(HallConstants.NOT_EXISTING_DB_LOCATION_ID);
        hall.setName(HallConstants.EXISTING_DB_NAME);
        hall.setSectors(new ArrayList<>());
        return hall;
    }


    public static HallDTO returnHallWithEmptyName() {
        HallDTO hall = new HallDTO();
        hall.setId(HallConstants.NEXT_DB_ID);
        hall.setLocationId(HallConstants.EXISTING_DB_LOCATION_ID);
        hall.setName("");
        hall.setSectors(new ArrayList<>());
        return hall;
    }

}
