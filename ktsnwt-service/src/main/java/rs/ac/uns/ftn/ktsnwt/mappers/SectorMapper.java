package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.model.Sector;

import java.util.List;
import java.util.stream.Collectors;

public class SectorMapper {

    private SectorMapper() {
        super();
    }

    public static SectorDTO toDto(Sector sector) {
        return new SectorDTO(sector);
    }

    public static List<SectorDTO> toListDto(List<Sector> sectors) {
        return sectors.stream().map(SectorDTO::new).collect(Collectors.toList());
    }
}
