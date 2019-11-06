package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.ktsnwt.dto.PricingDTO;
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
        List<Pricing> pricings = pricingService.findAll1();

        List<PricingDTO> pricingsDTO = new ArrayList<>();
        for (Pricing t : pricings) {
            pricingsDTO.add(new PricingDTO(t));
        }
        return new ResponseEntity<>(pricingsDTO, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<PricingDTO> getPricings(@PathVariable Long id){
        Pricing pricing = pricingService.findById1(id);
        if(pricing == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new PricingDTO(pricing), HttpStatus.OK);
    }


}
