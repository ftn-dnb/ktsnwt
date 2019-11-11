package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

public class HallMapper {

    private HallMapper() {

    }

    public static HallDTO toDto(Hall hall) {
        return new HallDTO(hall);
    }
}
