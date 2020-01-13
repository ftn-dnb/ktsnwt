package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByName(String name);
    List<Location> findAll();
}
