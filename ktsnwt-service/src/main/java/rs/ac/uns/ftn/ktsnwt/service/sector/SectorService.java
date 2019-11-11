package rs.ac.uns.ftn.ktsnwt.service.sector;

import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.model.Sector;

public interface SectorService {

    Sector findById(Long id);
    Sector addSector(SectorDTO sectorDTO);
    Sector editSector(SectorDTO sectorDTO);

}
