package rs.ac.uns.ftn.ktsnwt.service.pricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.repository.PricingRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PricingServiceImpl implements PricingService {

    @Autowired
    private PricingRepository pricingRepository;

    @Override
    public Pricing getPricing(Long id) {
        Pricing pricing = pricingRepository.getById(id);

        if (pricing == null)
            throw new ResourceNotFoundException("Pricing with id " + id + " doesn't exist.");

        return pricing;
    }

    @Override
    public List<Pricing> findAll() {
        return pricingRepository.findAll();
    }
}
