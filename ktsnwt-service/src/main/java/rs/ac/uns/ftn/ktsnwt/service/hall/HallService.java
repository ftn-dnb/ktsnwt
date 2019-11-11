package rs.ac.uns.ftn.ktsnwt.service.hall;

import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

public interface HallService {

    Hall findHallById(Long id);
    Hall addHall(Long locationId, HallDTO hallDTO);
    Hall editHall(HallDTO hallDTO);
}
