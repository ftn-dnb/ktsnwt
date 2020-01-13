package rs.ac.uns.ftn.ktsnwt.service.hall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.HallDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;
import rs.ac.uns.ftn.ktsnwt.repository.LocationRepository;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class HallServiceImpl implements HallService {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Hall> findAllById(Long id, int page, int size) {
        return hallRepository.getByLocationId(id, PageRequest.of(page, size)).toList();
    }

    public Hall findHallById(Long id) {
        try {
            return hallRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(
                    "Hall not found"
            );
        }
    }

    @Override
    public Hall addHall(Long locationId, HallDTO hallDTO) {
        Location location = locationRepository.findById(locationId).get();

        if (location == null) {
            throw new ResourceNotFoundException("Location with this id doesn't exist.");
        }

        if (hallRepository.findByName(hallDTO.getName(), hallDTO.getLocationId()) != null) {
            throw new ResourceAlreadyExistsException("Hall with that name already exists inside given location.");
        }

        Hall hall = new Hall(hallDTO);
        location.getHalls().add(hall);
        hall.setLocation(location);

        hallRepository.save(hall);

        return hall;
    }

    @Override
    public Hall editHall(HallDTO hallDTO) {
        if (hallRepository.findById(hallDTO.getId()).get() == null) {
            throw new ResourceNotFoundException("Hall with this id does not exist.");
        }
        Hall hall = hallRepository.findById(hallDTO.getId()).get();
        hall.setName(hallDTO.getName());
        hallRepository.save(hall);

        return hall;
    }
}
