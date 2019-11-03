package rs.ac.uns.ftn.ktsnwt.dto;

import javax.validation.constraints.NotNull;

public class PricingSeatDTO {

    private int row;
    private int seat;

    @NotNull(message = "Pricing is required")
    private Long pricingId;

    public PricingSeatDTO() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Long getPricingId() {
        return pricingId;
    }

    public void setPricingId(Long pricingId) {
        this.pricingId = pricingId;
    }
}
