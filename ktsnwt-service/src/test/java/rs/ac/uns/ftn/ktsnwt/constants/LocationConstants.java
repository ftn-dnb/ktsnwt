package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.dto.AddressDTO;
import rs.ac.uns.ftn.ktsnwt.dto.LocationDTO;
import rs.ac.uns.ftn.ktsnwt.model.Address;
import rs.ac.uns.ftn.ktsnwt.model.Location;

import java.util.ArrayList;

public class LocationConstants {

    private LocationConstants() {}

    //existing - valid
    public static final Long MOCK_ID = 1L;
    public static final String EXISTING_DB_NAME = "SPENS";
    public static final Long DB_ID = 1L;
    public static final int VALID_PAGE = 0;
    public static final int VALID_SIZE = 5;






    //not existing - not valid
    public static final Long NOT_EXISTING_DB_ID = 123L;
    public static final int NOT_EXISTING_PAGE = 123;
    public static final String NOT_EXISTING_DB_NAME = "SPENS123";
    public static final int NOT_VALID_PAGE = -3;
    public static final int NOT_VALID_SIZE = -5;



    //new
    public static final String NEW_DB_NAME = "SPENS1";
    public static final Long NEXT_DB_ID = 2L;




    public static Location returnMockedLocation() {
        Location location = new Location();
        location.setId(LocationConstants.MOCK_ID);
        return location;
    }


    public static LocationDTO returnDBLocationDTO(){
        LocationDTO l = new LocationDTO();
        Address a = new Address(AddressConstants.DB_ID, AddressConstants.DB_GOOGLE_API_ID, AddressConstants.DB_STREET_NAME, AddressConstants.DB_STREET_NUMBER, AddressConstants.DB_CITY, AddressConstants.DB_COUNTRY, AddressConstants.DB_POSTAL_CODE, AddressConstants.DB_LATITUDE, AddressConstants.DB_LONGITUDE);
        l.setAddress(new AddressDTO(a));
        l.setHalls(new ArrayList<>());
        l.setName(LocationConstants.EXISTING_DB_NAME);
        l.setId(LocationConstants.DB_ID);
        return l;
    }

    public static LocationDTO returnNewLocationDTO(){
        LocationDTO l = new LocationDTO();
        Address a = new Address(AddressConstants.DB_ID, AddressConstants.DB_GOOGLE_API_ID, AddressConstants.DB_STREET_NAME, AddressConstants.DB_STREET_NUMBER, AddressConstants.DB_CITY, AddressConstants.DB_COUNTRY, AddressConstants.DB_POSTAL_CODE, AddressConstants.DB_LATITUDE, AddressConstants.DB_LONGITUDE);
        l.setAddress(new AddressDTO(a));
        l.setHalls(new ArrayList<>());
        l.setName(LocationConstants.NEW_DB_NAME);
        l.setId(LocationConstants.NEXT_DB_ID);
        return l;
    }

    public static LocationDTO returnNewLocationDTONotFilledAddress(){
        LocationDTO l = new LocationDTO();
        Address a = new Address(AddressConstants.DB_ID, null, "", AddressConstants.DB_STREET_NUMBER, AddressConstants.DB_CITY, AddressConstants.DB_COUNTRY, AddressConstants.DB_POSTAL_CODE, AddressConstants.DB_LATITUDE, AddressConstants.DB_LONGITUDE);
        l.setAddress(new AddressDTO(a));
        l.setHalls(new ArrayList<>());
        l.setName(LocationConstants.NEW_DB_NAME);
        l.setId(LocationConstants.NEXT_DB_ID);
        return l;
    }


}
