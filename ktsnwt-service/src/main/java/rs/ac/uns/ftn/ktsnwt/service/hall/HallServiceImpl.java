package rs.ac.uns.ftn.ktsnwt.service.hall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;
import rs.ac.uns.ftn.ktsnwt.repository.LocationRepository;

@Service
public class HallServiceImpl implements HallService {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Hall addHall(Long locationId, HallDTO hallDTO) {
        if (locationRepository.findById(locationId).get() == null) {
            throw new ApiRequestException("Location with this id doesn't exist.");
        }

        Location location = locationRepository.findById(locationId).get();

        Hall hall = new Hall(hallDTO);
        location.getHalls().add(hall);
        hall.setLocation(location);

        hallRepository.save(hall);

        return hall;

    }

    @Override
    public Hall editHall(HallDTO hallDTO) {
        if (hallRepository.findById(hallDTO.getId()).get() == null) {
            throw new ApiRequestException("Hall with this id doesn't exist.");
        }
        Hall hall = hallRepository.findById(hallDTO.getId()).get();
        hall.setName(hallDTO.getName());
        hallRepository.save(hall);

        return hall;
    }
}
