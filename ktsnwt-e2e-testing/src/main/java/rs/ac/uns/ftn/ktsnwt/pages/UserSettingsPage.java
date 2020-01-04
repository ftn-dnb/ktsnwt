package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserSettingsPage {

    private WebDriver driver;

    @FindBy(css = "#username-header")
    private WebElement usernameHeader;

    @FindBy(css = "#firstname-input")
    private WebElement firstNameInput;

    @FindBy(css = "#lastname-input")
    private WebElement lastNameInput;

    @FindBy(css = "#email-input")
    private WebElement emailInput;

    @FindBy(css = "#old-pw-input")
    private WebElement oldPasswordInput;

    @FindBy(css = "#new-pw-input")
    private WebElement newPasswordInput;

    @FindBy(css = "#save-btn")
    private WebElement saveButton;

    @FindBy(css = "#change-pw-btn")
    private WebElement changePasswordButton;

    // TODO: private WebElement imageUplaodButton;


    public UserSettingsPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameHeader() {
        return usernameHeader;
    }

    public void ensureDataIsLoaded() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(usernameHeader));
    }

    public WebElement getToastMessage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.toast-message")));
    }

    public void setFirstName(String firstName) {
        this.firstNameInput.clear();
        this.firstNameInput.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        this.lastNameInput.clear();
        this.lastNameInput.sendKeys(lastName);
    }

    public void setEmail(String email) {
        this.emailInput.clear();
        this.emailInput.sendKeys(email);
    }

    public WebElement getFirstNameInput() {
        return firstNameInput;
    }

    public WebElement getLastNameInput() {
        return lastNameInput;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public WebElement getOldPasswordInput() {
        return oldPasswordInput;
    }

    public WebElement getNewPasswordInput() {
        return newPasswordInput;
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

    public WebElement getChangePasswordButton() {
        return changePasswordButton;
    }
}
