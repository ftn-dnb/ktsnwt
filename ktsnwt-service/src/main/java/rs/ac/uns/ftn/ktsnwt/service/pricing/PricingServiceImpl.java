package rs.ac.uns.ftn.ktsnwt.service.pricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.repository.PricingRepository;
import java.util.List;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;

@Service
public class PricingServiceImpl implements PricingService {

    @Autowired
    private PricingRepository pricingRepository;


    @Override
    public Pricing getPricing(Long id) {
        Pricing pricing = pricingRepository.findById(id).orElse(null);

        if (pricing == null)
            throw new ResourceNotFoundException("Pricing with id " + id + " doesn't exist.");

        return pricing;
    }

    @Override
    public List<Pricing> findAll() {
        return pricingRepository.findAll();
    }
}
