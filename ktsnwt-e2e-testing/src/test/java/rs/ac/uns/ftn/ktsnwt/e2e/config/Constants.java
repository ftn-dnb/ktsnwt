package rs.ac.uns.ftn.ktsnwt.e2e.config;

public class Constants {

    /**
     *   Change this constants according to your needs.
     */

    public static final String FRONTEND_APP_URL   = "http://localhost:4200/";
    public static final String CHROME_DRIVER_PATH = "C:/Users/mica/Downloads/chromedriver.exe";
    public static final String IMAGE_PATH = "C:/Users/mica/Desktop/70412898_1936293256525920_2135423663300673536_n.jpg";

    /**
     *   Error messages
     */

    public static final String REGISTRATION_BACKEND_ERROR = "There was an error while adding your account. Try again later.";
    public static final String REGISTRATION_DIFFERENT_PASSWORDS = "Passwords don't match";
    public static final String USER_SETTINGS_BACKEND_INFO_ERROR = "There was an error. Your data will not be changed for now.";
    public static final String USER_SETTINGS_BACKEND_PW_ERROR = "There was an error. Your password will remain the same";

    /**
     *   Success messages
     */

    public static final String USER_SETTINGS_INFO_CHANGE_SUCCESS = "Your profile info has been successfully changed.";
    public static final String USER_SETTINGS_PW_CHANGE_SUCCESS = "Your password has been changed. Please login again.";
    public static final String USER_SETTINGS_IMAGE_CHANGE_SUCCESS = "Your image has been successfully updated.";

    private Constants() {
    }
}
