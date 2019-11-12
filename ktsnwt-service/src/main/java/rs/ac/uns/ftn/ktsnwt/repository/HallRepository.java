package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.ktsnwt.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {

    Page<Hall> getByLocationId(Long id, Pageable pageable);
    Hall getById(Long id);

    @Query("SELECT h FROM Hall h WHERE h.location.id = :id and h.name like :hallName")
    Hall findByName(String hallName, Long id);
}
