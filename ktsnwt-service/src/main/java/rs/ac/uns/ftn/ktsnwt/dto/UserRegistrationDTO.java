package rs.ac.uns.ftn.ktsnwt.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserRegistrationDTO {

    @NotNull(message = "Username must be provided")
    private String username;

    @NotNull(message = "Email must be provided")
    @Email(regexp = ".+@.+\\..+", message = "Email is not valid")
    private String email;

    @NotNull(message = "Password must be provided")
    private String password;

    @NotNull(message = "Password repeat must be provided")
    private String repeatPassword;

    @NotNull(message = "First name must be provided")
    private String firstName;

    @NotNull(message = "Last name must be provided")
    private String lastName;


    public UserRegistrationDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
