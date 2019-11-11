package rs.ac.uns.ftn.ktsnwt.service.hall;

import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

import java.util.List;

public interface HallService {

<<<<<<< HEAD
    List<Hall> findAllById(Long id, int page, int size);
=======
    Hall findHallById(Long id);
>>>>>>> feature/sector-support
    Hall addHall(Long locationId, HallDTO hallDTO);
    Hall editHall(HallDTO hallDTO);
}
