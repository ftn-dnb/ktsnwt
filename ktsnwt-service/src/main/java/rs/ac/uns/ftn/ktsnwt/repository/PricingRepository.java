package rs.ac.uns.ftn.ktsnwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import java.util.List;

public interface PricingRepository extends JpaRepository<Pricing, Long> {
    List<Pricing> findAll();
    Pricing findById(long id);
    Pricing getById(Long id);


}
