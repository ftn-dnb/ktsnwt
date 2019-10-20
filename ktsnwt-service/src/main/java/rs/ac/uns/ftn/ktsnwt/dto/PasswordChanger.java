package rs.ac.uns.ftn.ktsnwt.dto;

public class PasswordChanger {

    private String oldPassword;
    private String newPassword;

    public PasswordChanger() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
