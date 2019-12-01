package rs.ac.uns.ftn.ktsnwt.constants;

import org.springframework.security.core.context.SecurityContext;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;

public class SectorConstants {

    private SectorConstants() {}

    public static final Long MOCK_ID = 1L;

    public static final Long MOCK_ID_RES = 4L;

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
