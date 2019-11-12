package rs.ac.uns.ftn.ktsnwt.service.pricing;

import rs.ac.uns.ftn.ktsnwt.model.Pricing;

import java.util.List;

public interface PricingService {
    List<Pricing> findAll();
    Pricing getPricing(Long id);
}
