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

    private UserConstants() {
    }

    public static User returnLoggedUser() {
        User user = new User();
        user.setId(UserConstants.DB_ID);
        user.setUsername(UserConstants.DB_USERNAME);
        user.setPassword(UserConstants.DB_PASSWORD);
        return user;
    }
}
