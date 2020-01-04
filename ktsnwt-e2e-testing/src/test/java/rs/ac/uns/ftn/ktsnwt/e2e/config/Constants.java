package rs.ac.uns.ftn.ktsnwt.e2e.config;

public class Constants {

    /**
     *   Change this constants according to your needs.
     */

    public static final String FRONTEND_APP_URL   = "http://localhost:4200/";
    public static final String CHROME_DRIVER_PATH = "C:/chromedriver/chromedriver.exe";


    /**
     *   Error messages
     */

    public static final String REGISTRATION_BACKEND_ERROR = "There was an error while adding your account. Try again later.";
    public static final String REGISTRATION_DIFFERENT_PASSWORDS = "Passwords don't match";

    /**
     *   Success messages
     */

    public static final String USER_SETTINGS_INFO_CHANGE_SUCCESS = "Your profile info has been successfully changed.";
    public static final String USER_SETTINGS_PW_CHANGE_SUCCESS = "Your password has been changed. Please login again.";

    private Constants() {
    }
}
