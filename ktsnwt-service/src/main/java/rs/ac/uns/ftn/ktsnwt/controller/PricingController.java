package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.ktsnwt.dto.PricingDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.PricingMapper;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;
import rs.ac.uns.ftn.ktsnwt.service.pricing.PricingService;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/pricings")
public class PricingController {
    @Autowired
    private PricingService pricingService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PricingDTO>> getPricings() {
        return new ResponseEntity<>(PricingMapper.toPricingDTOList(pricingService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<PricingDTO> findById(@PathVariable Long id){
        return new ResponseEntity<>(new PricingDTO(pricingService.getPricing(id)), HttpStatus.OK);

    }


}
