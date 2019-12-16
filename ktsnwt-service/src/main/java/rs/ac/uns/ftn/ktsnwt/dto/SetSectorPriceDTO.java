package rs.ac.uns.ftn.ktsnwt.dto;

public class SetSectorPriceDTO {

    private Long id; // sector id
    private double price;

    public SetSectorPriceDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
