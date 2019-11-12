package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.ktsnwt.model.Hall;
import rs.ac.uns.ftn.ktsnwt.model.Location;
import java.util.List;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByName(String name);
    List<Location> findAll();


}
