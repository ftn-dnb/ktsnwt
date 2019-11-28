package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.Sector;

public class SectorConstants {

    private SectorConstants() {}

    public static final Long DB_ID = 1L;

    public static Sector returnMockedSector() {
        Sector sector = new Sector();
        sector.setId(SectorConstants.DB_ID);
        sector.setHall(HallConstants.returnMockedHall());
        return sector;
    }
}
