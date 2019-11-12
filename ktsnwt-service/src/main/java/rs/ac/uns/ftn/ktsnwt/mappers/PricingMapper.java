package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.PricingDTO;
import rs.ac.uns.ftn.ktsnwt.model.Pricing;

import java.util.ArrayList;
import java.util.List;

public class PricingMapper {

    public PricingMapper() {
    }

    public static List<PricingDTO> toPricingDTOList(List<Pricing> pricings){
        List<PricingDTO> pricingsDTO = new ArrayList<>();
        for (Pricing t : pricings) {
            pricingsDTO.add(new PricingDTO(t));
        }
        return pricingsDTO;
    }
}
