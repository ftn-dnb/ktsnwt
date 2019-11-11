package rs.ac.uns.ftn.ktsnwt.service.sector;

import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.model.Sector;

import java.util.List;

public interface SectorService {

    Sector findById(Long id);
    List<Sector> findAllById(Long id, int page, int size);
    Sector addSector(SectorDTO sectorDTO);
    Sector editSector(SectorDTO sectorDTO);

}
