package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Pricing;

public class PricingDTO {
    private long id;
    private double price;

    public PricingDTO(Long id, double price) {
        this.id = id;
        this.price = price;
    }

    public PricingDTO(Pricing pricing) {
        this.id = pricing.getId();
        this.price = pricing.getPrice();
    }



    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public PricingDTO() {
    }
}
