package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.ktsnwt.model.User;

import java.sql.Timestamp;

public class UserConstants {

    // Login data for existing user (admin)
    public static final Long DB_ID = 2L;
    public static final String DB_USERNAME = "jane.doe";
    public static final String DB_PASSWORD = "123";


    public static final String DB_USERNAME_NON_EXIST = "jack.doe";


    //admin other info
    public static final String DB_EMAIL = "jane@doe.com";
    public static final boolean DB_ACTIVATED_ACCOUNT = true;
    public static final String DB_FIRST_NAME = "Jane";
    public static final String DB_LAST_NAME = "Doe";
    public static final String DB_IMAGE_PATH = "http://res.cloudinary.com/dhibcw0v1/image/upload/v1572021937/bgcbrwpatbahnbdkdboy.png";
    public static final String DB_PASSWORD_TIME = "2017-09-01 22:40:00";
    public static final Long DB_TICKETS = 2L;


    public static final Long DB_USER_ID = 1L;
    public static final String DB_USER_USERNAME = "john.doe";
    public static final String DB_USER_PASSWORD = "123";

    public static final Long MOCK_ID = 1L;
    public static final String MOCK_USERNAME = "jane.doe";
    public static final String MOCK_PASSWORD = "123";

    public static final String MOCK_NEW_PASSWORD = "234";

    public static final Long MOCK_ID_OTHER = 2L;
    public static final String MOCK_USERNAME_OTHER = "john.doe";
    public static final String MOCK_PASSWORD_OTHER = "123";


    public static final String NEW_USERNAME = "123";
    public static final String NEW_PASSWORD = "123";
    public static final String DEFAULT_IMAGE_PATH = "http://res.cloudinary.com/dhibcw0v1/image/upload/v1572021937/bgcbrwpatbahnbdkdboy.png";

    public static final Long MOCK_ID_ = 3L;

    private UserConstants() {
    }

    public static User returnLoggedUser() {
        User user = new User();
        user.setId(UserConstants.MOCK_ID);
        user.setUsername(UserConstants.MOCK_USERNAME);
        user.setPassword(UserConstants.MOCK_PASSWORD);
        return user;
    }

    public static User returnTicketUser() {
        User user = new User();
        user.setId(MOCK_ID_OTHER);
        user.setUsername(MOCK_USERNAME_OTHER);
        user.setPassword(MOCK_PASSWORD_OTHER);
        return user;
    }

    public static UserRegistrationDTO returnUserRegistrationDto(){
        UserRegistrationDTO user = new UserRegistrationDTO();
        user.setUsername("tarzan");
        user.setFirstName("Tarzan");
        user.setLastName("Tarzanic");
        user.setEmail("tarzan@gmail.com");
        user.setPassword("taki123");
        user.setRepeatPassword("taki123");
        return user;
    }
}
