package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;

public class AddressConstants {

    // Existing data in database
    public static final Long DB_ID = 1L;
    public static final String DB_CITY = "Novi Sad";
    public static final String DB_COUNTRY = "Srbija";
    public static final String DB_GOOGLE_API_ID = "12";
    public static final double DB_LATITUDE = 12;
    public static final double DB_LONGITUDE = 12;
    public static final String DB_POSTAL_CODE = "21000";
    public static final String DB_STREET_NAME = "Sutjeska";
    public static final String DB_STREET_NUMBER = "2";

    // This one doesn't exist in database
    public static final Long NON_EXISTING_DB_ID = 123456789L;
    public static final String NON_EXISTING_DB_GOOGLE_API_ID = "0123456789";

    // New address data for insert test
    public static final String NEW_DB_CITY = "Beograd";
    public static final String NEW_DB_COUNTRY = "Srbija";
    public static final String NEW_DB_GOOGLE_API_ID = "14";
    public static final double NEW_DB_LATITUDE = 14;
    public static final double NEW_DB_LONGITUDE = 14;
    public static final String NEW_DB_POSTAL_CODE = "11000";
    public static final String NEW_DB_STREET_NAME = "Novi bulevar";
    public static final String NEW_DB_STREET_NUMBER = "23A";

    // Data for mock object
    public static final Long MOCK_ID = 12345L;
    public static final String MOCK_GOOGLE_API_ID = "54321";

    public static AddressDTO createNewAddressDto() {
        AddressDTO address = new AddressDTO();
        address.setCity(AddressConstants.NEW_DB_CITY);
        address.setCountry(AddressConstants.NEW_DB_COUNTRY);
        address.setGoogleApiId(AddressConstants.NEW_DB_GOOGLE_API_ID);
        address.setLatitude(AddressConstants.NEW_DB_LATITUDE);
        address.setLongitude(AddressConstants.NEW_DB_LONGITUDE);
        address.setPostalCode(AddressConstants.NEW_DB_POSTAL_CODE);
        address.setStreetName(AddressConstants.NEW_DB_STREET_NAME);
        address.setStreetNumber(AddressConstants.NEW_DB_STREET_NUMBER);

        return address;
    }

    private AddressConstants() {
    }
}
