package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.ktsnwt.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query("SELECT s FROM Sector s WHERE s.hall.id = :id and s.name like :sectorName")
    Sector findByName(String sectorName, Long id);

    Page<Sector> getByHallId(Long id, Pageable pageable);
}
