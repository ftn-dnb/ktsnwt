package rs.ac.uns.ftn.ktsnwt.dto;

import rs.ac.uns.ftn.ktsnwt.model.Address;

public class AddressDTO {

    private Long id;
    private String googleApiId;
    private String streetName;
    private String streetNumber;
    private String city;
    private String country;
    private String postalCode;
    private double latitude;
    private double longitude;

    public AddressDTO() {
        super();
    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.googleApiId = address.getGoogleApiId();
        this.streetName = address.getStreetName();
        this.streetNumber = address.getStreetNumber();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleApiId() {
        return googleApiId;
    }

    public void setGoogleApiId(String googleApiId) {
        this.googleApiId = googleApiId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
