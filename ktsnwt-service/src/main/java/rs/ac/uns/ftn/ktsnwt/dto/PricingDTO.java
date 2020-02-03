package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Pricing;

public class PricingDTO {
    private long id;
    private double price;
    private long sectorId;

    public PricingDTO(Long id, double price) {
        this.id = id;
        this.price = price;
    }

    public PricingDTO(Pricing pricing) {
        this.id = pricing.getId();
        this.price = pricing.getPrice();
        this.sectorId = pricing.getSector().getId();
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getSectorId() {
        return sectorId;
    }

    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
    }

    public PricingDTO() {
    }
}
