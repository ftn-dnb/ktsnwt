package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.model.Sector;

public class SectorMapper {

    private SectorMapper() {
        super();
    }

    public static SectorDTO toDto(Sector sector) {
        return new SectorDTO(sector);
    }
}
