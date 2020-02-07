package rs.ac.uns.ftn.ktsnwt.service.sector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.dto.SectorDTO;
import rs.ac.uns.ftn.ktsnwt.exception.BadAttributeValueException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceAlreadyExistsException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Sector;
import rs.ac.uns.ftn.ktsnwt.repository.HallRepository;
import rs.ac.uns.ftn.ktsnwt.repository.SectorRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private HallRepository hallRepository;

    @Override
    public Sector findById(Long id) {
        try {
            return sectorRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(
                    "Sector not found"
            );
        }
    }

    @Override
    public List<Sector> findAllById(Long id, int page, int size) {
        return sectorRepository.getByHallId(id, PageRequest.of(page, size)).toList();
    }

    @Override
    public Sector addSector(SectorDTO sectorDTO) {
        Hall hall = hallRepository.findById(sectorDTO.getHallId()).get();

        if (hall == null) {
            throw new ResourceNotFoundException("" +
                    "Hall with that id does not exist."
            );
        }

        if (sectorRepository.findByName(sectorDTO.getName(), sectorDTO.getHallId()) != null) {
            throw new ResourceAlreadyExistsException("" +
                    "Sector with that name already exists in hall with that id."
            );
        }

        Sector sector = new Sector(sectorDTO);
        sector.setHall(hall);
        hall.getSectors().add(sector);

        sectorRepository.save(sector);

        return sector;
    }

    @Override
    public Sector editSector(SectorDTO sectorDTO) {
        Hall hall = hallRepository.findById(sectorDTO.getHallId()).get();

        if (hall == null) {
            throw new ResourceNotFoundException("" +
                    "Hall with that id does not exist."
            );
        }





        //if (sectorRepository.findByName(sectorDTO.getName(), sectorDTO.getHallId()) != null) {
        //    throw new ResourceAlreadyExistsException("" +
        //            "Sector with that name already exists in hall with that id."
        //    );
        //}

        Sector sector = sectorRepository.findById(sectorDTO.getId()).get();

        if (sector.getName() == null) {
            throw new BadAttributeValueException("");
        }

        sector.setName(sectorDTO.getName());
        sector.setCapacity(sectorDTO.getCapacity());
        sector.setNumColumns(sectorDTO.getNumColumns());
        sector.setNumRows(sectorDTO.getNumRows());
        sector.setType(sectorDTO.getType());

        sectorRepository.save(sector);

        return sector;
    }
}
