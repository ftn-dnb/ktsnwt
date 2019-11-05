package rs.ac.uns.ftn.ktsnwt.service.pricing;

import rs.ac.uns.ftn.ktsnwt.model.Pricing;

import java.util.List;

public interface PricingService {
    Pricing findById1(Long id);
    List<Pricing> findAll1();
}
