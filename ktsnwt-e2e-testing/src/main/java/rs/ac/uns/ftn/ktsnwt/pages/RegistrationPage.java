package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

    private WebDriver driver;

    @FindBy(css = "#firstname-input")
    private WebElement firstNameInputField;

    @FindBy(css = "#lastname-input")
    private WebElement lastNameInputField;

    @FindBy(css = "#username-input")
    private WebElement usernameInputField;

    @FindBy(css = "#email-input")
    private WebElement emailInputField;

    @FindBy(css = "#password-input")
    private WebElement passwordInputField;

    @FindBy(css = "#repeatpassword-input")
    private WebElement repeatPasswordInputField;

    @FindBy(css = "#register-btn")
    private WebElement registerButton;

    @FindBy(css = "#firstname-error-msg")
    private WebElement firstNameErrorMessage;

    @FindBy(css = "#lastname-error-msg")
    private WebElement lastNameErrorMessage;

    @FindBy(css = "#username-error-msg")
    private WebElement usernameErrorMessage;

    @FindBy(css = "#email-error-msg-req")
    private WebElement emailRequiredErrorMessage;

    @FindBy(css = "#email-error-msg")
    private WebElement emailErrorMessage;

    @FindBy(css = "#password-error-msg")
    private WebElement passwordErrorMessage;

    @FindBy(css = "#repeatpassword-error-msg")
    private WebElement repeatPasswordErrorMessage;

    @FindBy(css = "div.toast-message")
    private WebElement toastMessage;

    @FindBy(css = "div#request-sent")
    private WebElement activationSentScreen;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setFirstName(String firstName) {
        this.firstNameInputField.clear();
        this.firstNameInputField.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        this.lastNameInputField.clear();
        this.lastNameInputField.sendKeys(lastName);
    }

    public void setUsername(String username) {
        this.usernameInputField.clear();
        this.usernameInputField.sendKeys(username);
    }

    public void setEmail(String email) {
        this.emailInputField.clear();
        this.emailInputField.sendKeys(email);
    }

    public void setPassword(String password) {
        this.passwordInputField.clear();
        this.passwordInputField.sendKeys(password);
    }

    public void setRepeatPassword(String password) {
        this.repeatPasswordInputField.clear();
        this.repeatPasswordInputField.sendKeys(password);
    }

    public WebElement getRegisterButton() {
        return registerButton;
    }

    public WebElement getFirstNameErrorMessage() {
        return firstNameErrorMessage;
    }

    public WebElement getLastNameErrorMessage() {
        return lastNameErrorMessage;
    }

    public WebElement getUsernameErrorMessage() {
        return usernameErrorMessage;
    }

    public WebElement getEmailRequiredErrorMessage() {
        return emailRequiredErrorMessage;
    }

    public WebElement getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public WebElement getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public WebElement getRepeatPasswordErrorMessage() {
        return repeatPasswordErrorMessage;
    }

    public WebElement getFirstNameInputField() {
        return firstNameInputField;
    }

    public WebElement getLastNameInputField() {
        return lastNameInputField;
    }

    public WebElement getUsernameInputField() {
        return usernameInputField;
    }

    public WebElement getEmailInputField() {
        return emailInputField;
    }

    public WebElement getPasswordInputField() {
        return passwordInputField;
    }

    public WebElement getRepeatPasswordInputField() {
        return repeatPasswordInputField;
    }

    public WebElement getToastMessage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.toast-message")));
    }

    public WebElement getActivationSentScreen() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#request-sent")));
    }
}
