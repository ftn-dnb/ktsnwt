package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {

    Hall getById(Long id);
}
