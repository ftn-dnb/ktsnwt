package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.model.Ticket;
import java.util.List;

public interface PricingRepository extends JpaRepository<Pricing, Long> {
    Page<Pricing> findAll(Pageable page);
    Pricing getById(Long id);

}
