package rs.ac.uns.ftn.ktsnwt.service.pricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.repository.PricingRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PricingServiceImpl implements PricingService {

    @Autowired
    private PricingRepository pricingRepository;


    @Override
    public Pricing findById1(Long id) {
        try {
            return pricingRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ApiRequestException("Pricing with id '" + id + "' doesn't exist.");
        }
    }

    @Override
    public List<Pricing> findAll1() {
        return pricingRepository.findAll();
    }
}
