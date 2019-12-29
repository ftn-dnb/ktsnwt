package rs.ac.uns.ftn.ktsnwt.constants;

import rs.ac.uns.ftn.ktsnwt.model.User;

public class UserConstants {

    // Login data for existing user (admin)
    public static final Long DB_ID = 1L;
    public static final String DB_USERNAME = "jane.doe";
    public static final String DB_PASSWORD = "123";


    public static final Long DB_USER_ID = 1L;
    public static final String DB_USER_USERNAME = "john.doe";
    public static final String DB_USER_PASSWORD = "123";

    public static final Long MOCK_ID = 1L;
    public static final String MOCK_USERNAME = "jane.doe";
    public static final String MOCK_PASSWORD = "123";

    public static final Long MOCK_ID_OTHER = 2L;
    public static final String MOCK_USERNAME_OTHER = "john.doe";
    public static final String MOCK_PASSWORD_OTHER = "123";


    public static final String NEW_USERNAME = "123";
    public static final String NEW_PASSWORD = "123";
    public static final String DEFAULT_IMAGE_PATH = "http://res.cloudinary.com/dhibcw0v1/image/upload/v1572021937/bgcbrwpatbahnbdkdboy.png";

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
}
