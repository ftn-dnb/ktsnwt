package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;
import org.springframework.security.core.context.SecurityContext;
import rs.ac.uns.ftn.ktsnwt.model.Sector;

public class SectorConstants {
    private SectorConstants() {}

    //first sector original values
    public static final Long ORIGINAL_ID = 1L;
    public static final int ORIGINAL_CAPACITY = 4;
    public static final int ORIGINAL_ROWS = 2;
    public static final int ORIGINAL_COLUMNS = 2;
    public static final String ORIGINAL_NAME = "Sedista";
    public static final Long ORIGINAL_HALL_ID = 1L;
    public static final Long NEXT_DB_ID = 4L;


    //existing / valid
    public static final Long EXISTING_DB_ID = 1L;
    public static final int EXISTING_PAGE = 0;
    public static final int VALID_SIZE = 5;
    public static final Long EXISTING_DB_HALL_ID = 1L;
    public static final String RANDOM_NAME = "new name";
    public static final String EXISTING_NAME = "Parter";
    public static final String ANOTHER_EXISTING_NAME = "Sedista";
    public static final Long ANOTHER_EXISTING_DB_HALL_ID = 2L;


    //new
    public static final int NEW_VALID_CAPACITY = 20;
    public static final int NEW_VALID_ROWS = 5;
    public static final int NEW_VALID_COLUMNS = 4;
    public static final Long NEW_VALID_HALL_ID = 2L;
    public static final String NEW_VALID_NAME = "sector4";
    public static final int NEW_INVALID_CAPACITY = -20;
    public static final int NEW_INVALID_ROWS = -5;
    public static final int NEW_INVALID_COLUMNS = -4;


    //non-existing / not valid
    public static final int NOT_VALID_SIZE = -1;
    public static final int NOT_VALID_PAGE = -1;
    public static final int NOT_EXISTING_PAGE = 1234;
    public static final Long NOT_EXISTING_HALL_ID = 1000L;
    public static final Long NOT_EXISTING_DB_ID = 123L;




    public static final Long MOCK_ID = 1L;

    public static final Long MOCK_ID_RES = 4L;

  
  
    public static SectorDTO createNewSectorDTO(){
        SectorDTO s = new SectorDTO();
        s.setCapacity(NEW_VALID_CAPACITY);
        s.setHallId(NEW_VALID_HALL_ID);
        s.setName(NEW_VALID_NAME);
        s.setNumColumns(NEW_VALID_COLUMNS);
        s.setNumRows(NEW_VALID_ROWS);
        s.setType(SectorType.SEATS);
        return s;
    }

    public static SectorDTO createExistingSectorDTO(){
        SectorDTO s = new SectorDTO();
        s.setCapacity(NEW_VALID_CAPACITY);
        s.setHallId(EXISTING_DB_HALL_ID);
        s.setName(ANOTHER_EXISTING_NAME);
        s.setNumColumns(NEW_VALID_COLUMNS);
        s.setNumRows(NEW_VALID_ROWS);
        s.setType(SectorType.SEATS);
        return s;
    }

    public static SectorDTO createSectorDTOWithNotExistingHallId(){
        SectorDTO s = new SectorDTO();
        s.setCapacity(NEW_VALID_CAPACITY);
        s.setHallId(NOT_EXISTING_HALL_ID);
        s.setName(ANOTHER_EXISTING_NAME);
        s.setNumColumns(NEW_VALID_COLUMNS);
        s.setNumRows(NEW_VALID_ROWS);
        s.setType(SectorType.SEATS);
        return s;
    }

    public static SectorDTO createSectorDTOInvalidCalculatingCapacity(){
        SectorDTO s = new SectorDTO();
        s.setCapacity(NEW_VALID_CAPACITY + 1);
        s.setHallId(ANOTHER_EXISTING_DB_HALL_ID);
        s.setName(ANOTHER_EXISTING_NAME);
        s.setNumColumns(NEW_VALID_COLUMNS);
        s.setNumRows(NEW_VALID_ROWS);
        s.setType(SectorType.SEATS);
        return s;
    }

    //invalid capacity = less than 1
    public static SectorDTO createSectorDTOInvalidCapacity(){
        SectorDTO s = new SectorDTO();
        s.setCapacity(NEW_INVALID_CAPACITY);
        s.setHallId(ANOTHER_EXISTING_DB_HALL_ID);
        s.setName(ANOTHER_EXISTING_NAME);
        s.setNumColumns(NEW_VALID_COLUMNS);
        s.setNumRows(NEW_VALID_ROWS);
        s.setType(SectorType.SEATS);
        return s;
    }

    //invalid rows = less than 1
    public static SectorDTO createSeatSectorDTOInvalidRows(){
        SectorDTO s = new SectorDTO();
        s.setCapacity(NEW_VALID_CAPACITY);
        s.setHallId(ANOTHER_EXISTING_DB_HALL_ID);
        s.setName(ANOTHER_EXISTING_NAME);
        s.setNumColumns(NEW_VALID_COLUMNS);
        s.setNumRows(NEW_INVALID_ROWS);
        s.setType(SectorType.SEATS);
        return s;
    }

    public static SectorDTO createSeatSectorDTOInvalidColumns() {
        SectorDTO s = new SectorDTO();
        s.setCapacity(NEW_VALID_CAPACITY);
        s.setHallId(ANOTHER_EXISTING_DB_HALL_ID);
        s.setName(ANOTHER_EXISTING_NAME);
        s.setNumColumns(NEW_INVALID_COLUMNS);
        s.setNumRows(NEW_VALID_ROWS);
        s.setType(SectorType.SEATS);
        return s;
    }

    public static Sector returnMockedSector() {
        Sector sector = new Sector();
        sector.setId(SectorConstants.MOCK_ID);
        sector.setHall(HallConstants.returnMockedHall());
        return sector;
    }

    public static Sector returnSectorRes() {
        Sector sector = new Sector();
        sector.setId(SectorConstants.MOCK_ID_RES);
        sector.setType(SectorType.SEATS);
        sector.setCapacity(10);
        sector.setNumRows(6);
        sector.setNumColumns(5);
        return sector;
    }
}
