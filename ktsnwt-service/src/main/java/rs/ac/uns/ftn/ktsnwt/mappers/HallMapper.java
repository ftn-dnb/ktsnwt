package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

import java.util.List;
import java.util.stream.Collectors;

public class HallMapper {

    private HallMapper() {

    }

    public static HallDTO toDto(Hall hall) {
        return new HallDTO(hall);
    }

    public static List<HallDTO> toListDto(List<Hall> halls) {
        return halls.stream().map(HallDTO::new).collect(Collectors.toList());
    }
}

